package com.partnerd.web.dto.projectDTO;

import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class ProjectRequestDTO {

    // 프로젝트 팀원 검색
    @Getter
    @Setter
    public static class FindProjectMemberDTO {
        private String keyword;
    }

    // 프로젝트 모집글 생성
    @Getter
    @Setter
    public static class CreateProjectDTO {
        private String title;   // 제목
        private String info;    // 한 줄 소개
        private String description; // 설명
        private String current_progress;    // 현재 개발 및 발전 상황
        private String skill;   // 필요한 역량
        private String part;    // 필요한 파트
        private String recruitNum;  // 모집 인원
        private String dev_stack;   // 개발 기술 스택
        private String pm_stack;    // 기획 기술 스택
        private String design_stack;    // 디자인 기술 스택
        private Date startDate; // 시작 날짜
        private Date endDate; // 종료 날짜
        private String thumbnailKeyName;  // 프로젝트 대표 사진
        private List<String> projectImgKeyNameList; // 프로젝트 사진들
        private List<Long> projectMember;   // 함께한 팀원
        private List<Long> projectCategoryPrefer;  // 프로젝트 카테고리
        private Set<ContactMethodDTO> contactMethod;   // 컨택트 방법
    }

    // 프로젝트 모집글 수정
    @Getter
    @Setter
    public static class UpdateProjectDTO {
        private String title;   // 제목
        private String info;    // 한 줄 소개
        private String description; // 설명
        private String current_progress;    // 현재 개발 및 발전 상황
        private String skill;   // 필요한 역량
        private String part;    // 필요한 파트
        private String recruitNum;  // 모집 인원
        private String dev_stack;   // 개발 기술 스택
        private String pm_stack;    // 기획 기술 스택
        private String design_stack;    // 디자인 기술 스택
        private Date startDate; // 시작 날짜
        private Date endDate; // 종료 날짜
        private String thumbnailKeyName;  // 프로젝트 대표 사진
        private List<String> projectImgKeyNameList; // 프로젝트 사진들
        private List<Long> projectMember;   // 함께한 팀원
        private List<Long> projectCategoryPrefer;  // 프로젝트 카테고리
        private Set<ContactMethodDTO> contactMethod;   // 컨택트 방법
    }

}
