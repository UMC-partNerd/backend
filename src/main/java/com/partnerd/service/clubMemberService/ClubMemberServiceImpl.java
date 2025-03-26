package com.partnerd.service.clubMemberService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ClubHandler;
import com.partnerd.apiPaylaod.exception.handler.ClubMemberHandler;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.domain.Club;
import com.partnerd.domain.Member;
import com.partnerd.domain.enums.ActiveType;
import com.partnerd.domain.enums.ClubMemberRole;
import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepository;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepositoryCustom;
import com.partnerd.repository.clubRepository.ClubRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.web.dto.memberDTO.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubMemberServiceImpl implements ClubMemberService {
    private final ClubMemberRepositoryCustom clubMemberRepositoryCustom;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    // 파트너드(동아리) 리더 권한 위임
    @Override
    @Transactional
    public ClubMember putChangeClubLeader(Long clubId, Long newLeaderId, Long leaderId){
        // clubId에 해당하는 클럽이 있는지 확인
        Club club = clubRepository.findById(clubId).orElseThrow(() -> {
            throw new ClubHandler(ErrorStatus.CLUB_NOT_FOUND);
        });

        // leaderId의 소유자가 클럽 멤버인지 확인
        ClubMember oldLeader = clubMemberRepositoryCustom.findByClubIdAndMemberId(clubId, leaderId).orElseThrow(() -> {
            throw new ClubMemberHandler(ErrorStatus.CLUB_MEMBER_NOT_FOUND);
        });

        // leaderId의 소유자가 클럽의 리더가 아닐 경우 동아리 리더 위임 권한 제한
        if (!oldLeader.getRole().equals(ClubMemberRole.LEADER)) {
            throw new ClubMemberHandler(ErrorStatus.CLUB_NOT_AUTHORIZED);
        }

        // newLeaderId와 leaderId가 같을 경우 리더 권한 위임 불가
        if (Objects.equals(newLeaderId, leaderId)) {
            throw new ClubMemberHandler(ErrorStatus.CANNOT_ASSIGN_ROLE_TO_SELF);
        }

        // newLeaderId의 소유자가 클럽 멤버인지 확인
        ClubMember newLeader = clubMemberRepositoryCustom.findByClubIdAndMemberId(clubId, newLeaderId)
                .orElseGet(() -> {
                    // 존재하지 않으면 새로운 클럽 멤버 생성
                    Member member = memberRepository.findById(newLeaderId).orElseThrow(() -> {
                        throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
                    });

                    ClubMember newClubMember = ClubMember.builder()
                            .role(ClubMemberRole.LEADER)  // 바로 리더로 설정
                            .joined_date(new Date())
                            .status(ActiveType.ACTIVE)
                            .club(club)
                            .member(member)
                            .build();

                    return clubMemberRepository.save(newClubMember);
                });

        // 리더 변경 처리
        oldLeader.setRole(ClubMemberRole.MEMBER);
        newLeader.setRole(ClubMemberRole.LEADER);

        return newLeader;
    }

    // 파트너드(동아리) 멤버 비활성화
    @Override
    @Transactional
    public ClubMember putChangeMemberActvice(Long clubId, Long memberId, Long leaderId){
        // clubId에 해당하는 클럽이 있는지 확인
        clubRepository.findById(clubId).orElseThrow(() -> {
            throw new ClubHandler(ErrorStatus.CLUB_NOT_FOUND);
        });

        // leaderId의 소유자가 클럽 멤버인지 확인
        ClubMember oldLeader = clubMemberRepositoryCustom.findByClubIdAndMemberId(clubId, leaderId).orElseThrow(() -> {
            throw new ClubMemberHandler(ErrorStatus.CLUB_MEMBER_NOT_FOUND);
        });

        // leaderId의 소유자가 클럽의 리더가 아닐 경우 동아리 멤버 활동 상태 변경 권한 제한
        if (!oldLeader.getRole().equals(ClubMemberRole.LEADER)) {
            throw new ClubMemberHandler(ErrorStatus.CLUB_NOT_AUTHORIZED);
        }

        // memberId의 소유자가 클럽 멤버인지 확인
        ClubMember clubMember = clubMemberRepositoryCustom.findByClubIdAndMemberId(clubId, memberId).orElseThrow(() -> {
            throw new ClubMemberHandler(ErrorStatus.CLUB_MEMBER_NOT_FOUND);
        });

        // 멤버의 활동 상태가 비활성화인지 확인
        if(clubMember.getStatus().equals(ActiveType.INACTIVE)){
            throw new ClubMemberHandler(ErrorStatus.CLUB_MEMBER_ALREADY_INACTIVE);
        }

        // 비활성화 처리
        clubMember.setStatus(ActiveType.INACTIVE);

        return clubMember;
    }

    //파트너드 멤버 조회 API
    @Override
    public List<MemberResponseDTO.ClubMemberDTO> getClubMembers(Long clubId) {
        List<ClubMember> clubMembers = clubMemberRepository.findByClubId(clubId);

        // 클럽 멤버가 없을 경우, 예외 대신 빈 리스트 반환
        if (clubMembers.isEmpty()) {
            return Collections.emptyList(); // 빈 리스트 반환
        }

        return clubMembers.stream()
                .filter(clubMember -> clubMember.getMember() != null)
                .map(clubMember -> MemberResponseDTO.ClubMemberDTO.builder()
                        .nickname(clubMember.getMember().getNickname())
                        .occupationOfInterest(clubMember.getMember().getOccupation_of_interest())
                        .profileKeyName(clubMember.getMember().getProfile_url())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
