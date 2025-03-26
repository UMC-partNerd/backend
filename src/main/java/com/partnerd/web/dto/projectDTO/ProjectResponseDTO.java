package com.partnerd.web.dto.projectDTO;

import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import com.partnerd.web.dto.memberDTO.MemberResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
        private String thumbnailKeyName;
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
    
    // 프로젝트 모집글 상세페이지 보기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDetailDTO{
        private String title;   // 제목
        private String intro;   // 한줄 소개
        private String description;     // 프로젝트 설명
        private String current_progress; // 개발 상황 및 발전 방향
        private String dev_stack;   // 개발 기술 스택
        private String pm_stack;    // 기획 기술 스택
        private String design_stack;    // 디자인 기술 스택
        private String part;    // 필요한 파트
        private String recruitNum;  // 필요한 인원
        private String skill;   // 필요한 역량
        private Date startDate; // 모집 시작일
        private Date endDate;   // 모집 마감일
        private String thumbnailKeyName;  // 프로젝트 대표 사진
        private List<String> projectImgKeyNameList; // 프로젝트 사진들
        private MemberResponseDTO.MemberForProjectDetailDTO leaderInfo; // 리더(작성자) 정보
        private Set<ProjectCategoryDTO> projectCategories; // 개발 카테고리
        private Set<ProjectMemberDTO> projectMembers;  // 함께한 팀원
        private Set<ContactMethodDTO> contactMethods;  // 컨택
    }

    // 마이페이지 - 내가 쓴 프로젝트 모집글 모아보기 (한 칸씩)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageProjectPreviewDTO {
        private Long projectId;
        private String title;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // 마이페이지 - 내가 쓴 프로젝트 모집글 모아보기 (전체 리스트)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageProjectPreviewListDTO {
        private Long memberId;
        private List<MypageProjectPreviewDTO> mypagePprojectPreviewDTOList;
    }
}
