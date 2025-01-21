package com.partnerd.web.dto.projectDTO;

import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import lombok.*;

import java.util.List;

public class PromotionProjectRequestDTO {

    // 프로젝트 홍보 생성
    @Getter
    @Setter
    public static class CreatePromotionProjectDTO {
        private String title;   // 제목
        private String info;    // 한 줄 소개
        private String description; // 설명
        private List<Long> promotionProjectMember;   // 함께한 팀원
        private List<ContactMethodDTO> contactMethod;   // 컨택트 방법
    }

    // 프로젝트 홍보 수정
    @Getter
    @Setter
    public static class UpdatePromotionProjectDTO {
        private String title;   // 제목
        private String info;    // 한 줄 소개
        private String description; // 설명
        private List<Long> promotionProjectMember;   // 함께한 팀원
        private List<ContactMethodDTO> contactMethod;   // 컨택트 방법
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
