package com.partnerd.web.dto.clubDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ClubResponseDTO {
    // 파트너드 목록 조회(마이페이지-한 칸씩)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubPreviewDTO {
        private String profile;
        private String category;
        private String name;
        private String intro;
    }

    // 파트너드 목록 조회(마이페이지)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadClubPreviewListDTO {
        private List<ClubPreviewDTO> clubPreviewDTOList;
    }
}
