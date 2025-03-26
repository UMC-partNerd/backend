package com.partnerd.converter.clubConverter;

import com.partnerd.mongoRepository.domain.mapping.ClubMembershipRequest;
import com.partnerd.web.dto.clubDTO.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    // 파트너드(동아리) 가입 요청
    public static ClubResponseDTO.ClubJoinRequestResultDTO addClubMembershipRequestDTO(ClubMembershipRequest clubMembershipRequest){
        return ClubResponseDTO.ClubJoinRequestResultDTO.builder()
                .memberId(clubMembershipRequest.getMember().getId())
                .clubId(clubMembershipRequest.getClub().getId())
                .requestId(clubMembershipRequest.getId())
                .status(clubMembershipRequest.getStatus().toString())
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 파트너드(동아리) 가입 요청 목록 변환
    public static ClubResponseDTO.ClubJoinRequestListDTO clubJoinRequestDTOList(Long clubId, List<ClubMembershipRequest> clubMembershipRequests) {
        List<ClubResponseDTO.ClubJoinRequestDTO> requestDTOList = clubMembershipRequests.stream()
                .map(request -> ClubResponseDTO.ClubJoinRequestDTO.builder()
                        .memberId(request.getMember().getId())
                        .requestId(request.getId())
                        .status(request.getStatus().toString())
                        .profileKeyName(request.getMember().getProfile_url())
                        .nickname(request.getMember().getNickname())
                        .occupationOfInterest(request.getMember().getOccupation_of_interest())
                        .belongToClub(request.getMember().getBelong_to_club())
                        .build())
                .collect(Collectors.toList());

        return ClubResponseDTO.ClubJoinRequestListDTO.builder()
                .clubId(clubId)
                .clubJoinRequestDTOList(requestDTOList)
                .build();
    }
}
