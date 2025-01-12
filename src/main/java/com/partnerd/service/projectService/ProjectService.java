package com.partnerd.service.projectService;


import com.partnerd.domain.Project;
import com.partnerd.web.dto.projectDTO.ProjectRequestDTO;

public interface ProjectService {

    // 프로젝트 모집글 생성
    Project addProject(ProjectRequestDTO.CreateProjectDTO request);


    // 프로젝트 모집글 수정
    Project updateProject(ProjectRequestDTO.UpdateProjectDTO request, Long projectId);

}
