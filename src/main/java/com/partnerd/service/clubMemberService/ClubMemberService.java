package com.partnerd.service.clubMemberService;

import com.partnerd.domain.mapping.ClubMember;

public interface ClubMemberService {
    // 파트너드(동아리) 리더 권한 위임
    ClubMember putChangeClubLeader(Long clubId, Long newLeaderId, Long leaderId);
}