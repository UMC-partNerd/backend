package com.partnerd.service.clubMembershipRequestService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ClubHandler;
import com.partnerd.apiPaylaod.exception.handler.ClubMembershipRequestHandler;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.domain.Club;
import com.partnerd.domain.Member;
import com.partnerd.domain.enums.ActiveType;
import com.partnerd.domain.enums.ClubMemberRole;
import com.partnerd.domain.enums.RequestStatus;
import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.domain.mapping.ClubMembershipRequest;
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

//
//@Service
//@RequiredArgsConstructor
//public class ClubMembershipRequestServiceImpl implements ClubMembershipRequestService {
//    private final ClubMembershipRequestRepository clubMembershipRequestRepository;
//    private final ClubMembershipRequestRepositoryCustom clubMembershipRequestRepositoryCustom;
//    private final ClubMemberRepositoryCustom clubMemberRepositoryCustom;
//    private final ClubMemberRepository clubMemberRepository;
//    private final ClubRepository clubRepository;
//    private final MemberRepository memberRepository;
//
//    // 파트너드(동아리) 가입 요청 승인
//    @Override
//    @Transactional
//    public ClubMembershipRequest putClubJoinApprove(Long leaderId, ClubRequestDTO.ClubJoinDTO request){
//        // leaderId의 소유자가 클럽 멤버인지 확인
//        ClubMember clubMember = clubMemberRepositoryCustom.findByClubIdAndMemberId(request.getClubId(), leaderId).orElseThrow(() -> {
//            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBER_NOT_FOUND);
//        });
//
//        // 클럽 멤버의 리더가 아닐 경우 동아리 가입 요청 승인 권한 제한
//        if (!clubMember.getRole().equals(ClubMemberRole.LEADER)) {
//            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_NOT_AUTHORIZED);
//        }
//
//        // 동아리 가입 요청 정보가 없을 경우 가입 승인 제한
//        ClubMembershipRequest newClubMembershipRequest = clubMembershipRequestRepositoryCustom.findByRequest(request.getMemberId(), request.getClubId(), request.getRequestId()).orElseThrow(() -> {
//            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBER_REQUEST_NOT_FOUND);
//        });
//
//        // 동아리 가입 요청자가 이미 동아리 회원인 경우 예외 처리
//        clubMemberRepositoryCustom.findByClubIdAndMemberId(request.getClubId(), request.getMemberId()).ifPresent((checkClubMember) -> {
//            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBER_ALREADY_EXISTS);
//        });
//
//        // ClubMembershipRequest 엔티티 status 업데이트
//        newClubMembershipRequest.changeStatus(RequestStatus.APPROVED);
//
//        // ClubMember에 추가
//        Member approvedMember = newClubMembershipRequest.getMember();
//
//        if (approvedMember == null) {
//            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
//        }
//
//        ClubMember newClubMember = ClubMember.builder()
//                .member(approvedMember)
//                .club(clubMember.getClub())
//                .role(ClubMemberRole.MEMBER) // 기본 역할 설정
//                .joined_date(new Date()) // 현재 날짜로 설정
//                .status(ActiveType.ACTIVE) // 기본 활동 상태 설정
//                .build();
//
//        clubMemberRepository.save(newClubMember);
//
//        return clubMembershipRequestRepository.save(newClubMembershipRequest);
//    }
//
//    // 파트너드(동아리) 가입 요청 거절
//    @Override
//    @Transactional
//    public ClubMembershipRequest putClubJoinReject(Long leaderId, ClubRequestDTO.ClubJoinDTO request){
//        // leaderId의 소유자가 클럽 멤버인지 확인
//        ClubMember clubMember = clubMemberRepositoryCustom.findByClubIdAndMemberId(request.getClubId(), leaderId).orElseThrow(() -> {
//            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBER_NOT_FOUND);
//        });
//
//        // 클럽 멤버의 리더가 아닐 경우 동아리 가입 요청 거절 권한 제한
//        if (!clubMember.getRole().equals(ClubMemberRole.LEADER)) {
//            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_NOT_AUTHORIZED);
//        }
//
//        // 동아리 가입 요청 정보가 없을 경우 가입 거절 제한
//        ClubMembershipRequest newClubMembershipRequest = clubMembershipRequestRepositoryCustom.findByRequest(request.getMemberId(), request.getClubId(), request.getRequestId()).orElseThrow(() -> {
//            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBER_REQUEST_NOT_FOUND);
//        });
//
//        // 동아리 가입 요청자가 이미 동아리 회원인 경우 요청 거부
//        clubMemberRepositoryCustom.findByClubIdAndMemberId(request.getClubId(), request.getMemberId()).ifPresent((checkClubMember) -> {
//            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBER_ALREADY_EXISTS);
//        });
//
//        // 동아리 가입 요청자의 가입 요청이 이미 거부된 상태일 경우
//        clubMembershipRequestRepositoryCustom.findByMemberIdAndClubId(request.getMemberId(), request.getClubId())
//                .ifPresent((checkClubMembershipRequest) -> {
//                    if (RequestStatus.REJECTED.equals(checkClubMembershipRequest.getStatus())) {
//                        throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBERSHIP_REQUEST_REJECTED);
//                    }
//                });
//
//        // ClubMembershipRequest 엔티티 status 업데이트
//        newClubMembershipRequest.changeStatus(RequestStatus.REJECTED);
//
//        return clubMembershipRequestRepository.save(newClubMembershipRequest);
//    }
//
//    //파트너드(동아리) 가입 요청
//    @Override
//    @Transactional
//    public ClubMembershipRequest addClubMembershipRequest(Long memberId, Long clubId){
//        // clubId에 해당하는 클럽이 있는지 확인
//        Club club = clubRepository.findById(clubId).orElseThrow(() -> {
//            throw new ClubHandler(ErrorStatus.CLUB_NOT_FOUND);
//        });
//
//        // memberId로 Member 데이터 가져오기
//        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
//            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
//        });
//
//        // 동아리 가입 요청자가 이미 동아리 회원인 경우 요청 거부
//        clubMemberRepositoryCustom.findByClubIdAndMemberId(clubId, memberId).ifPresent((checkClubMember) -> {
//            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBER_ALREADY_EXISTS);
//        });
//
//        // 사용자가 해당 클럽에 가입을 요청한 적 있는지 확인
//        Optional<ClubMembershipRequest> request = clubMembershipRequestRepositoryCustom.findByMemberIdAndClubId(memberId, clubId);
//
//        if (request.isEmpty()) {
//            // 가입 요청이 없을 경우, 새 요청 데이터 생성
//            ClubMembershipRequest newRequest = ClubMembershipRequest.builder()
//                    .status(RequestStatus.PENDING)
//                    .member(member)
//                    .club(club)
//                    .build();
//            return clubMembershipRequestRepository.save(newRequest);
//        }
//
//        // 기존 요청이 있는 경우 상태 확인 및 처리
//        ClubMembershipRequest existingRequest = request.get();
//
//        switch (existingRequest.getStatus()) {
//            case PENDING:
//                throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBERSHIP_ALREADY_REQUESTED);
//            case APPROVED:
//                throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBERSHIP_ALREADY_APPROVED);
//            default:
//                existingRequest.setStatus(RequestStatus.PENDING);
//                return existingRequest;
//        }
//    }
//    // 파트너드(동아리) 가입 요청 목록 조회
//    @Override
//    @Transactional
//    public List<ClubMembershipRequest> getClubJoinRequestList(Long leaderId, Long clubId) {
//        // clubId에 해당하는 클럽이 있는지 확인
//        Club club = clubRepository.findById(clubId).orElseThrow(() -> {
//            throw new ClubHandler(ErrorStatus.CLUB_NOT_FOUND);
//        });
//
//        // leaderId의 소유자가 클럽 멤버인지 확인
//        ClubMember clubMember = clubMemberRepositoryCustom.findByClubIdAndMemberId(clubId, leaderId).orElseThrow(() -> {
//            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_MEMBER_NOT_FOUND);
//        });
//
//        // 클럽 멤버의 리더가 아닐 경우 동아리 가입 요청 거절 권한 제한
//        if (!clubMember.getRole().equals(ClubMemberRole.LEADER)) {
//            throw new ClubMembershipRequestHandler(ErrorStatus.CLUB_NOT_AUTHORIZED);
//        }
//
//        // 상태가 PENDING인 가입 요청 목록 조회
//        return clubMembershipRequestRepositoryCustom.findAllPendingRequestsByClubId(clubId);
//    }
//}
