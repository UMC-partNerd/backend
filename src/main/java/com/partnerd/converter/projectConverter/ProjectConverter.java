package com.partnerd.converter.projectConverter;


import com.partnerd.domain.Project;
import com.partnerd.web.dto.projectDTO.ProjectRequestDTO;
import com.partnerd.web.dto.projectDTO.ProjectResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProjectConverter {

    // 프로젝트 모집글 생성
    public static Project toProject(ProjectRequestDTO.CreateProjectDTO request){

        return Project.builder()
                .title(request.getTitle())
                .intro(request.getInfo())
                .description(request.getDescription())
                .current_progress(request.getCurrent_progress())
                .skill(request.getSkill())
                .part(request.getPart())
                .recruitNum(request.getRecruitNum())
                .dev_stack(request.getDev_stack())
                .pm_stack(request.getPm_stack())
                .design_stack(request.getDesign_stack())
                .projectMemberList(new ArrayList<>())
                .projectCategoryPreferList(new ArrayList<>())
                .build();
    }

    public static ProjectResponseDTO.CreateProjectResultDTO toCreateProjectResultDTO(Project project){
        return ProjectResponseDTO.CreateProjectResultDTO.builder()
                .projectId(project.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 프로젝트 모집글 수정
    public static ProjectResponseDTO.UpdateProjectResultDTO toUpdateProjectResultDTO(Project project){
        return ProjectResponseDTO.UpdateProjectResultDTO.builder()
                .projectId(project.getId())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
