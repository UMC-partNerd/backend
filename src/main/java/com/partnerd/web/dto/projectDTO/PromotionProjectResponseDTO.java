package com.partnerd.web.dto.projectDTO;

import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import com.partnerd.web.dto.memberDTO.MemberResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
        private String thumbnailKeyName;
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

    // 프로젝트 홍보글 상세페이지 보기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromotionProjectDetailDTO{
        private String title;   // 제목
        private String intro;   // 한줄 소개
        private String description; // 설명
        private Long vote;  // 투표수
        private String thumbnailKeyName;  // 프로젝트 대표 사진
        private List<String> projectImgKeyNameList; // 프로젝트 사진들
        private MemberResponseDTO.MemberForProjectDetailDTO leaderInfo; // 리더(작성자) 정보
        private Set<PromotionProjectMemberDTO> promotionProjectMembers;   // 팀원
        private Set<ContactMethodDTO> contactMethods;   // 컨택
    }

    // 마이페이지 - 내가 쓴 프로젝트 홍보글 모아보기 (한 칸씩)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypagePromotionProjectPreviewDTO {
        private Long promotionProjectId;
        private String title;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // 마이페이지 - 내가 쓴 프로젝트 홍보글 모아보기 (전체 리스트)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypagePromotionProjectPreviewListDTO {
        private Long memberId;
        private List<PromotionProjectResponseDTO.MypagePromotionProjectPreviewDTO> mypagePromotionPprojectPreviewDTOList;
    }
}
