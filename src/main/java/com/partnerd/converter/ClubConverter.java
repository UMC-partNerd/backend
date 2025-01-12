package com.partnerd.converter;

import com.partnerd.domain.Club;
import com.partnerd.web.dto.clubDTO.ClubRegisterRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubRegisterResponseDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateResponseDTO;

public class ClubConverter {

    public static Club toClubEntity(ClubRegisterRequestDTO dto) {
        return Club.builder()
                .name(dto.getName())
                .intro(dto.getIntro())
                .contact_Method(dto.getContact())
                .category(dto.getCategory()) // category 필드 매핑
                .profile(null) // 이미지 처리 미구현
                .views(0L)
                .build();
    }

    public static Club toClubEntity(ClubUpdateRequestDTO dto) {
        return Club.builder()
                .name(dto.getName())
                .intro(dto.getIntro())
                .contact_Method(dto.getContact())
                .category(dto.getCategory())
                .profile(null) // 이미지 처리 미구현
                .views(0L)
                .build();
    }

    public static ClubRegisterResponseDTO toClubRegisterResponseDTO(Club club) {
        return new ClubRegisterResponseDTO(club.getId(), club.getName(), club.getCategory());
    }



    public static ClubUpdateResponseDTO toClubUpdateResponseDTO(Club club) {
        return new ClubUpdateResponseDTO(club.getId(), club.getName(), club.getCategory());
    }
}
