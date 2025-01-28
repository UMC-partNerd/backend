package com.partnerd.service.clubMembershipRequestService;

import com.partnerd.domain.mapping.ClubMembershipRequest;
import com.partnerd.web.dto.clubDTO.ClubRequestDTO;

public interface ClubMembershipRequestService {
    // 파트너드(동아리) 가입 요청 승인
    ClubMembershipRequest putClubJoinApprove(Long leaderId, ClubRequestDTO.ClubJoinDTO request);
}