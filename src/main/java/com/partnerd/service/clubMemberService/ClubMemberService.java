package com.partnerd.service.clubMemberService;

import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.web.dto.clubDTO.ClubMemberResponseDTO;
import com.partnerd.web.dto.memberDTO.MemberResponseDTO;

import java.util.List;

public interface ClubMemberService {
    // 파트너드(동아리) 리더 권한 위임
    ClubMember putChangeClubLeader(Long clubId, Long newLeaderId, Long leaderId);

    // 파트너드(동아리) 멤버 비활성화
    ClubMember putChangeMemberActvice(Long clubId, Long memberId, Long leaderId);

    //파트너드 멤버 조회
    List<MemberResponseDTO.ClubMemberDTO> getClubMembers(Long clubId);

}