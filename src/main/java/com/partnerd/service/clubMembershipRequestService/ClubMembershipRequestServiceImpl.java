package com.partnerd.service.clubMembershipRequestService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ClubHandler;
import com.partnerd.apiPaylaod.exception.handler.ClubMembershipRequestHandler;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.mongoRepository.domain.Club;
import com.partnerd.mongoRepository.domain.Member;
import com.partnerd.mongoRepository.domain.enums.ActiveType;
import com.partnerd.mongoRepository.domain.enums.ClubMemberRole;
import com.partnerd.mongoRepository.domain.enums.RequestStatus;
import com.partnerd.mongoRepository.domain.mapping.ClubMember;
import com.partnerd.mongoRepository.domain.mapping.ClubMembershipRequest;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepository;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepositoryCustom;
import com.partnerd.repository.clubMembershipRequestRepository.ClubMembershipRequestRepository;
import com.partnerd.repository.clubMembershipRequestRepository.ClubMembershipRequestRepositoryCustom;
import com.partnerd.repository.clubRepository.ClubRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.web.dto.clubDTO.ClubRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubMembershipRequestServiceImpl implements ClubMembershipRequestService {
    private final ClubMembershipRequestRepository clubMembershipRequestRepository;
    private final ClubMembershipRequestRepositoryCustom clubMembershipRequestRepositoryCustom;
    private final ClubMemberRepositoryCustom clubMemberRepositoryCustom;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    // 클럽 존재 여부 검증
    private Club validateClubExists(Long clubId) {
        return clubRepository.findById(clubId).orElseThrow(() -> {
            throw new ClubHandler(ErrorStatus.CLUB_NOT_FOUND);
        });
    }

    // 리더 권한 검증
    private ClubMember validateLeader(Long clubId, Long leaderId) {
        ClubMember clubMember = clubMemberRepositoryCustom.findByClubIdAndMemberId(clubId, leaderId).orElseThrow(() -> {
            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBER_NOT_FOUND);
        });

        if (!clubMember.getRole().equals(ClubMemberRole.LEADER)) {
            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_NOT_AUTHORIZED);
        }

        return clubMember;
    }

    // 가입 요청 유효성 검증
    private ClubMembershipRequest validateMembershipRequest(Long memberId, Long clubId, Long requestId) {
        return clubMembershipRequestRepositoryCustom.findByRequest(memberId, clubId, requestId).orElseThrow(() -> {
            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBER_REQUEST_NOT_FOUND);
        });
    }

    // 멤버 존재 여부 검증
    private Member validateMemberExists(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        });
    }

    // 멤버가 이미 동아리에 속했는지 검증
    private void validateNotAlreadyMember(Long clubId, Long memberId) {
        clubMemberRepositoryCustom.findByClubIdAndMemberId(clubId, memberId).ifPresent((checkClubMember) -> {
            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBER_ALREADY_EXISTS);
        });
    }

    // 파트너드(동아리) 가입 요청 승인
    @Override
    @Transactional
    public ClubMembershipRequest putClubJoinApprove(Long leaderId, ClubRequestDTO.ClubJoinDTO request) {
        validateClubExists(request.getClubId());
        validateLeader(request.getClubId(), leaderId);

        ClubMembershipRequest newClubMembershipRequest = validateMembershipRequest(request.getMemberId(), request.getClubId(), request.getRequestId());
        validateNotAlreadyMember(request.getClubId(), request.getMemberId());

        // 가입 요청 승인 처리
        newClubMembershipRequest.changeStatus(RequestStatus.APPROVED);

        // ClubMember 추가
        Member approvedMember = newClubMembershipRequest.getMember();
        if (approvedMember == null) {
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        ClubMember newClubMember = ClubMember.builder()
                .member(approvedMember)
                .club(newClubMembershipRequest.getClub())
                .role(ClubMemberRole.MEMBER)
                .joined_date(new Date())
                .status(ActiveType.ACTIVE)
                .build();

        clubMemberRepository.save(newClubMember);
        return clubMembershipRequestRepository.save(newClubMembershipRequest);
    }

    // 파트너드(동아리) 가입 요청 거절
    @Override
    @Transactional
    public ClubMembershipRequest putClubJoinReject(Long leaderId, ClubRequestDTO.ClubJoinDTO request) {
        validateClubExists(request.getClubId());
        validateLeader(request.getClubId(), leaderId);

        ClubMembershipRequest newClubMembershipRequest = validateMembershipRequest(request.getMemberId(), request.getClubId(), request.getRequestId());
        validateNotAlreadyMember(request.getClubId(), request.getMemberId());

        // 가입 요청 거절 처리
        newClubMembershipRequest.changeStatus(RequestStatus.REJECTED);
        return clubMembershipRequestRepository.save(newClubMembershipRequest);
    }

    // 파트너드(동아리) 가입 요청
    @Override
    @Transactional
    public ClubMembershipRequest addClubMembershipRequest(Long memberId, Long clubId) {
        validateClubExists(clubId);
        Member member = validateMemberExists(memberId);

        validateNotAlreadyMember(clubId, memberId);

        // 가입 요청 처리
        Optional<ClubMembershipRequest> request = clubMembershipRequestRepositoryCustom.findByMemberIdAndClubId(memberId, clubId);
        if (request.isEmpty()) {
            ClubMembershipRequest newRequest = ClubMembershipRequest.builder()
                    .status(RequestStatus.PENDING)
                    .member(member)
                    .club(validateClubExists(clubId))
                    .build();
            return clubMembershipRequestRepository.save(newRequest);
        }

        ClubMembershipRequest existingRequest = request.get();
        if (existingRequest.getStatus() == RequestStatus.PENDING) {
            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBERSHIP_ALREADY_REQUESTED);
        } else if (existingRequest.getStatus() == RequestStatus.APPROVED) {
            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBERSHIP_ALREADY_APPROVED);
        }

        existingRequest.setStatus(RequestStatus.PENDING);
        return existingRequest;
    }

    // 파트너드(동아리) 가입 요청 목록 조회
    @Override
    @Transactional
    public List<ClubMembershipRequest> getClubJoinRequestList(Long leaderId, Long clubId) {
        validateClubExists(clubId);
        validateLeader(clubId, leaderId);

        // 상태가 PENDING인 가입 요청 목록 조회
        return clubMembershipRequestRepositoryCustom.findAllPendingRequestsByClubId(clubId);
    }
}