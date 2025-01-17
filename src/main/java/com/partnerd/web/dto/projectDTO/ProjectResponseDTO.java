package com.partnerd.web.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ProjectResponseDTO {

    // 프로젝트 모집글 생성
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateProjectResultDTO {
        private Long projectId;
        private LocalDateTime createdAt;
    }

    // 프로젝트 모집글 수정
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateProjectResultDTO {
        private Long projectId;
        private LocalDateTime updatedAt;
    }
}
