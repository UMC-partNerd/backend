package com.partnerd.converter;

import com.partnerd.domain.Category;
import com.partnerd.domain.Club;
import com.partnerd.web.dto.clubDTO.*;

import java.util.ArrayList;
import java.util.List;

public class ClubConverter {

    public static Club toClubEntity(ClubRegisterRequestDTO dto, Category category) {
        return Club.builder()
                .name(dto.getName())
                .intro(dto.getIntro())
                .category(category) // category 필드 매핑
                .profile(null) // 이미지 처리 미구현
                .views(0L)
                .contactMethodList(new ArrayList<>())
                .clubMembers(new ArrayList<>())
                .build();

    }

 /*   public static Club toClubEntity(ClubUpdateRequestDTO dto) {
        return Club.builder()
                .name(dto.getName())
                .intro(dto.getIntro())

                .category(dto.getCategory())
                .profile(null) // 이미지 처리 미구현
                .views(0L)
                .build();
    }*/

    public static ClubRegisterResponseDTO toClubRegisterResponseDTO(Club club) {
        return new ClubRegisterResponseDTO(club.getId(), club.getName(), club.getCategory().getId(),
                club.getCategory().getName());

    }



    public static ClubUpdateResponseDTO toClubUpdateResponseDTO(Club club) {
        return new ClubUpdateResponseDTO(club.getId(), club.getName(), club.getCategory().getId(),
                club.getCategory().getName());
    }

    public static ClubDTO toClubDTO(Club club){
        return new ClubDTO(
                club.getProfile(),
                club.getName(),
                club.getIntro()
        );
    }

    // 파트너드 목록 조회(마이페이지)
    public static ClubResponseDTO.ReadClubPreviewListDTO clubPreviewListDTO(List<Club> clubs) {
        List<ClubResponseDTO.ClubPreviewDTO> clubPreviewDTOList = clubs.stream()
                .map(club -> ClubResponseDTO.ClubPreviewDTO.builder()
                        .profile(club.getProfile())
                        .category(club.getCategory().getName()) // 카테고리 이름
                        .name(club.getName())
                        .intro(club.getIntro())
                        .build())
                .toList();

        return ClubResponseDTO.ReadClubPreviewListDTO.builder()
                .clubPreviewDTOList(clubPreviewDTOList)
                .build();
    }
}
