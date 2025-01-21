package com.partnerd.web.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PromotionProjectResponseDTO {

    // 프로젝트 홍보 생성
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePromotionProjectResultDTO {
        private Long promotionProjectId;
        private LocalDateTime createdAt;
    }

    // 프로젝트 홍보 수정
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePromotionProjectResultDTO {
        private Long promotionProjectId;
        private LocalDateTime updatedAt;
    }

    // 프로젝트 홍보글 모아보기 (한 칸씩)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromotionProjectPreviewDTO {
        private Long promotionProjectId;
        private String title;
        private String intro;
    }

    // 프로젝트 홍보글 모아보기 (전체 리스트)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromotionProjectPreviewListDTO{
        private List<PromotionProjectPreviewDTO> promotionProjectPreviewDTOList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
}
