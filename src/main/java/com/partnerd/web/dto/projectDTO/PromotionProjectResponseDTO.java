package com.partnerd.web.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
