package com.partnerd.converter.clubConverter;

import com.partnerd.domain.mapping.ClubMembershipRequest;
import com.partnerd.web.dto.clubDTO.*;

import java.time.LocalDateTime;

public class ClubMembershipRequestConverter {

    // 파트너드(동아리) 가입 요청 승인
    public static ClubResponseDTO.ClubJoinResultDTO clubJoinApproveDTO(ClubMembershipRequest clubMembershipRequest){
        return ClubResponseDTO.ClubJoinResultDTO.builder()
                .memberId(clubMembershipRequest.getMember().getId())
                .clubId(clubMembershipRequest.getClub().getId())
                .requestId(clubMembershipRequest.getId())
                .status(clubMembershipRequest.getStatus().toString())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // 파트너드(동아리) 가입 요청 거절
    public static ClubResponseDTO.ClubJoinResultDTO clubJoinRejectDTO(ClubMembershipRequest clubMembershipRequest){
        return ClubResponseDTO.ClubJoinResultDTO.builder()
                .memberId(clubMembershipRequest.getMember().getId())
                .clubId(clubMembershipRequest.getClub().getId())
                .requestId(clubMembershipRequest.getId())
                .status(clubMembershipRequest.getStatus().toString())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
