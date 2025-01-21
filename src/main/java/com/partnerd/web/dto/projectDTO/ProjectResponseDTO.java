package com.partnerd.web.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    // 프로젝트 모집글 모아보기 (한 칸씩)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectPreviewDTO {
        private Long projectId;
        private String projectStatus;
        private String title;
        private String intro;
        private List<ProjectCategoryDTO> categoryDTOList;
    }

    // 프로젝트 모집글 모아보기 (전체 리스트)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectPreviewListDTO {
        private List<ProjectPreviewDTO> projectPreviewDTOList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }
}
