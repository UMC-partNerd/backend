package com.partnerd.web.dto.projectDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProjectRequestDTO {

    // 프로젝트 모집글 생성
    @Getter
    @Setter
    public static class CreateProjectDTO {
        String title;   // 제목
        String info;    // 한 줄 소개
        String description; // 설명
        String current_progress;    // 현재 개발 및 발전 상황
        String skill;   // 필요한 역량
        String part;    // 필요한 파트
        String recruitNum;  // 모집 인원
        String dev_stack;   // 개발 기술 스택
        String pm_stack;    // 기획 기술 스택
        String design_stack;    // 디자인 기술 스택
        List<Long> projectMember;   // 함께한 팀원
        List<Long> projectCategoryPrefer;  // 프로젝트 카테고리
    }

    // 프로젝트 모집글 수정
    @Getter
    @Setter
    public static class UpdateProjectDTO {
        String title;   // 제목
        String info;    // 한 줄 소개
        String description; // 설명
        String current_progress;    // 현재 개발 및 발전 상황
        String skill;   // 필요한 역량
        String part;    // 필요한 파트
        String recruitNum;  // 모집 인원
        String dev_stack;   // 개발 기술 스택
        String pm_stack;    // 기획 기술 스택
        String design_stack;    // 디자인 기술 스택
        List<Long> projectMember;   // 함께한 팀원
        List<Long> projectCategoryPrefer;  // 프로젝트 카테고리
    }

}
