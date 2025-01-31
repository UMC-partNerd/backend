package com.partnerd.converter.projectConverter;


import com.partnerd.domain.Project;
import com.partnerd.domain.ProjectImage;
import com.partnerd.domain.enums.ImageType;
import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import com.partnerd.web.dto.memberDTO.MemberResponseDTO;
import com.partnerd.web.dto.projectDTO.ProjectCategoryDTO;
import com.partnerd.web.dto.projectDTO.ProjectMemberDTO;
import com.partnerd.web.dto.projectDTO.ProjectRequestDTO;
import com.partnerd.web.dto.projectDTO.ProjectResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .projectMemberList(new HashSet<>())
                .projectCategoryPreferList(new HashSet<>())
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

    // 프로젝트 모집글 모아보기 (한칸씩)
    public static ProjectResponseDTO.ProjectPreviewDTO projectPreviewDTO(Project project) {

        Date now = new Date();

        List<ProjectCategoryDTO> projectCategoryDTOS =  project.getProjectCategoryPreferList().stream()
                .map(projectCategory -> {
                    ProjectCategoryDTO projectCategoryDTO = ProjectCategoryDTO.builder()
                            .id(projectCategory.getProjectCategory().getId())
                            .name(projectCategory.getProjectCategory().getName())
                            .build();
                    return projectCategoryDTO;
                }).collect(Collectors.toList());

        String status = (project.getEndDate() != null && project.getEndDate().before(now))
                ? "모집완료"
                : "모집중";

        String thumbnailKeyName = project.getProjectImageList().stream()
                .filter(image -> image.getImageType() == ImageType.THUMBNAIL)
                .map(ProjectImage::getKeyName)
                .findFirst()
                .orElse(null);

        return ProjectResponseDTO.ProjectPreviewDTO.builder()
                .projectId(project.getId())
                .projectStatus(status)
                .title(project.getTitle())
                .intro(project.getIntro())
                .thumbnailKeyName(thumbnailKeyName)
                .categoryDTOList(projectCategoryDTOS)
                .build();
    }

    // 프로젝트 모집글 모아보기 (전체 리스트)
    public static ProjectResponseDTO.ProjectPreviewListDTO projectPreviewListDTO(Page<Project> projectPage) {
        List<ProjectResponseDTO.ProjectPreviewDTO> projectPreviewDTOList =
                projectPage.stream().map(ProjectConverter::projectPreviewDTO).collect(Collectors.toList());

        return ProjectResponseDTO.ProjectPreviewListDTO.builder()
                .projectPreviewDTOList(projectPreviewDTOList)
                .listSize(projectPage.getSize())
                .totalPage(projectPage.getTotalPages())
                .totalElements(projectPage.getTotalElements())
                .isFirst(projectPage.isFirst())
                .isLast(projectPage.isLast())
                .build();
    }

    // 프로젝트 모집글 상세페이지 조회
    public static ProjectResponseDTO.ProjectDetailDTO toProjectDetailDTO(Project project) {

        String thumbnailKeyName = project.getProjectImageList().stream()
                .filter(image -> image.getImageType() == ImageType.THUMBNAIL)
                .map(ProjectImage::getKeyName)
                .findFirst()
                .orElse(null);

        List<String> projectImgKeyNameList = project.getProjectImageList().stream()
                .filter(image -> image.getImageType() == ImageType.INTRO)
                .map(ProjectImage::getKeyName)
                .collect(Collectors.toList());

        return ProjectResponseDTO.ProjectDetailDTO.builder()
                .title(project.getTitle())
                .intro(project.getIntro())
                .description(project.getDescription())
                .current_progress(project.getCurrent_progress())
                .dev_stack(project.getDev_stack())
                .pm_stack(project.getPm_stack())
                .design_stack(project.getDesign_stack())
                .part(project.getPart())
                .recruitNum(project.getRecruitNum())
                .skill(project.getSkill())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .thumbnailKeyName(thumbnailKeyName)
                .projectImgKeyNameList(projectImgKeyNameList)
                .contactMethods(project.getContactMethodList().stream()
                        .map(ContactMethodDTO::toContactMethodDTO)
                        .collect(Collectors.toSet()))
                .projectMembers(project.getProjectMemberList().stream()
                        .map(ProjectMemberDTO::toProjectMemberDTO)
                        .collect(Collectors.toSet()))
                .projectCategories(project.getProjectCategoryPreferList().stream()
                        .map(category -> ProjectCategoryDTO.builder()
                                .id(category.getProjectCategory().getId())
                                .name(category.getProjectCategory().getName())
                                .build())
                        .collect(Collectors.toSet()))
                .leaderInfo(MemberResponseDTO.MemberForProjectDetailDTO.builder()
                        .id(project.getMember().getId())
                        .name(project.getMember().getName())
                        .occupation_of_interest(project.getMember().getOccupation_of_interest())
                        .belong_to_club(project.getMember().getBelong_to_club())
                        .build())
                .build();
    }

    // 마이페이지 - 내가 쓴 프로젝트 모집글 모아보기
    public static ProjectResponseDTO.MypageProjectPreviewListDTO toMyProjectsDTO(Long memberId, List<Project> projects) {
        List<ProjectResponseDTO.MypageProjectPreviewDTO> projectPreviewDTOList = projects.stream()
                .map(project -> ProjectResponseDTO.MypageProjectPreviewDTO.builder()
                        .projectId(project.getId()) // 프로젝트 ID
                        .title(project.getTitle()) // 프로젝트 제목
                        .description(project.getDescription()) // 프로젝트 설명
                        .createdAt(project.getCreatedAt()) // 생성 날짜
                        .updatedAt(project.getUpdatedAt()) // 수정 날짜
                        .build())
                .toList();

        return ProjectResponseDTO.MypageProjectPreviewListDTO.builder()
                .memberId(memberId) // 사용자 ID
                .mypagePprojectPreviewDTOList(projectPreviewDTOList) // 변환된 프로젝트 DTO 리스트
                .build();
    }
}
