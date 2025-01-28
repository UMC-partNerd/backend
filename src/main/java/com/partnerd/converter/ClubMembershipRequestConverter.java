package com.partnerd.converter;

import com.partnerd.domain.Category;
import com.partnerd.domain.Club;
import com.partnerd.domain.Member;
import com.partnerd.domain.mapping.ClubMembershipRequest;
import com.partnerd.web.dto.clubDTO.*;
import com.partnerd.web.dto.memberDTO.MemberResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
}
