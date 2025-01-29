package com.partnerd.service.projectService;


import com.partnerd.domain.Project;
import com.partnerd.web.dto.projectDTO.ProjectRequestDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectService {

    // 프로젝트 모집글 생성
    Project addProject(Long memberId, ProjectRequestDTO.CreateProjectDTO request);


    // 프로젝트 모집글 수정
    Project updateProject(Long memberId, ProjectRequestDTO.UpdateProjectDTO request, Long projectId);

    // 프로젝트 모집글 삭제
    Void deleteProject(Long memberId, Long projectId);

    // 프로젝트 모집글 모아보기
    Page<Project> getProjectList(Integer page,Integer status, List<Long> category, String keyword);
    
    // 프로젝트 모집글 상세페이지 조회
    Project getProject(Long projectId);

    // 마이페이지 - 내가 쓴 프로젝트 모집글 모아보기
    List<Project> getMyProjects(Long memberId);
}
