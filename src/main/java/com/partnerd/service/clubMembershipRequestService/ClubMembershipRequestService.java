package com.partnerd.service.clubMembershipRequestService;

import com.partnerd.domain.mapping.ClubMembershipRequest;
import com.partnerd.web.dto.clubDTO.ClubRequestDTO;

import java.util.List;

public interface ClubMembershipRequestService {
    // 파트너드(동아리) 가입 요청 승인
    ClubMembershipRequest putClubJoinApprove(Long leaderId, ClubRequestDTO.ClubJoinDTO request);

    // 파트너드(동아리) 가입 요청 거절
    ClubMembershipRequest putClubJoinReject(Long leaderId, ClubRequestDTO.ClubJoinDTO request);

    // 파트너드(동아리) 가입 요청
    ClubMembershipRequest addClubMembershipRequest(Long memberId, Long clubId);

    // 파트너드(동아리) 가입 요청 목록 조회
    List<ClubMembershipRequest> getClubJoinRequestList(Long leaderId, Long clubId);
}