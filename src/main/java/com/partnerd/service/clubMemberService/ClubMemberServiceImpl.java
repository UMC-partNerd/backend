package com.partnerd.service.clubMemberService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ClubHandler;
import com.partnerd.apiPaylaod.exception.handler.ClubMemberHandler;
import com.partnerd.domain.enums.ClubMemberRole;
import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepositoryCustom;
import com.partnerd.repository.clubRepository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClubMemberServiceImpl implements ClubMemberService {
    private final ClubMemberRepositoryCustom clubMemberRepositoryCustom;
    private final ClubRepository clubRepository;

    // 파트너드(동아리) 리더 권한 위임
    @Override
    @Transactional
    public ClubMember putChangeClubLeader(Long clubId, Long newLeaderId, Long leaderId){
        // clubId에 해당하는 클럽이 있는지 확인
        clubRepository.findById(clubId).orElseThrow(() -> {
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
        ClubMember newLeader = clubMemberRepositoryCustom.findByClubIdAndMemberId(clubId, newLeaderId).orElseThrow(() -> {
            throw new ClubMemberHandler(ErrorStatus.CLUB_MEMBER_NOT_FOUND);
        });

        // 리더 변경 처리
        oldLeader.setRole(ClubMemberRole.MEMBER);
        newLeader.setRole(ClubMemberRole.LEADER);

        return newLeader;
    }
}
