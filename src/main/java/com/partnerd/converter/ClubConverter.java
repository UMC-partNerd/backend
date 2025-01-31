package com.partnerd.converter;

import com.partnerd.domain.Category;
import com.partnerd.domain.Club;
import com.partnerd.domain.ClubActivity;
import com.partnerd.domain.ClubImage;
import com.partnerd.domain.enums.ImageType;
import com.partnerd.web.dto.clubDTO.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ClubConverter {

    public static Club toClubEntity(ClubRegisterRequestDTO dto, Category category) {
        return Club.builder()
                .name(dto.getName())
                .intro(dto.getIntro())
                .category(category) // 카테고리 필드 매핑
                .views(0L)
                .contactMethodList(new ArrayList<>()) // 초기화
                .clubImgList(new LinkedHashSet<>()) // 초기화
                .clubMembers(new ArrayList<>()) // 초기화
                .build();

    }

    //클럽액티비티 생성
    public static ClubActivity toClubActivityEntity(ClubActivityDTO dto) {
        return ClubActivity.builder()
                .intro(dto.getIntro())
                .clubActivityImageList(new LinkedHashSet<>()) //  활동 이미지 리스트 초기화
                .build();
    }



    public static ClubRegisterResponseDTO toClubRegisterResponseDTO(Club club) {
        return new ClubRegisterResponseDTO(club.getId(), club.getName(), club.getCategory().getId(),
                club.getCategory().getName());

    }



    public static ClubUpdateResponseDTO toClubUpdateResponseDTO(Club club) {
        return new ClubUpdateResponseDTO(club.getId(), club.getName(), club.getCategory().getId(),
                club.getCategory().getName());
    }

    public static ClubDTO toClubDTO(Club club) {
        String profileImage = club.getClubImgList().stream()
                .filter(img -> img.getImage_type() == ImageType.MAIN) // MAIN 타입 찾기
                .map(ClubImage::getKeyName)
                .findFirst()
                .orElse(null); // 없으면 null 반환

        return new ClubDTO(
                profileImage,
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
