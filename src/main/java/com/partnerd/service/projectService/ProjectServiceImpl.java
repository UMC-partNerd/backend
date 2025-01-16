package com.partnerd.service.projectService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ProjectHandler;
import com.partnerd.converter.projectConverter.ProjectCategoryPreferConverter;
import com.partnerd.converter.projectConverter.ProjectConverter;
import com.partnerd.converter.projectConverter.ProjectMemberConverter;
import com.partnerd.domain.ContactMethod;
import com.partnerd.domain.Member;
import com.partnerd.domain.Project;
import com.partnerd.domain.ProjectCategory;
import com.partnerd.domain.mapping.ProjectCategoryPrefer;
import com.partnerd.domain.mapping.ProjectMember;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.projectRepository.ProjectCategoryPreferRepository;
import com.partnerd.repository.projectRepository.ProjectCategoryRepository;
import com.partnerd.repository.projectRepository.ProjectMemberRepository;
import com.partnerd.repository.projectRepository.ProjectRepository;
import com.partnerd.web.dto.projectDTO.ProjectRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ProjectCategoryRepository projectCategoryRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectCategoryPreferRepository projectCategoryPreferRepository;

    // 프로젝트 모집글 생성
    @Override
    @Transactional
    public Project addProject(ProjectRequestDTO.CreateProjectDTO request) {
        Project newProject = ProjectConverter.toProject(request);


        // 팀원 추가
        List<Member> memberList = request.getProjectMember().stream()
                .map(memberId -> memberRepository.findById(memberId)
                        .orElseThrow(() -> new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_ID_NOT_FOUND)))
                .collect(Collectors.toList());

        List<ProjectMember> projectMemberList = ProjectMemberConverter.toProjectMemberList(memberList);

        projectMemberList.forEach(projectMember -> {projectMember.setProject(newProject);});

        // 카테고리 추가
        List<ProjectCategory> projectCategoryList = request.getProjectCategoryPrefer().stream()
                .map(projectCategoryId -> projectCategoryRepository.findById(projectCategoryId)
                        .orElseThrow(() -> new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_ID_NOT_FOUND)))
                .collect(Collectors.toList());

        List<ProjectCategoryPrefer> projectCategoryPreferList = ProjectCategoryPreferConverter.toProjectCategoryPreferList(projectCategoryList);

        projectCategoryPreferList.forEach(projectCategoryPrefer -> {projectCategoryPrefer.setProject(newProject);});


        // 컨택트 방식
        if (request.getContactMethod() != null) {
            List<ContactMethod> contactMethods = request.getContactMethod().stream()
                    .map(contactMethodDTO -> {
                        ContactMethod contactMethod = ContactMethod.builder()
                                .contactType(contactMethodDTO.getContactType())
                                .contactUrl(contactMethodDTO.getContactUrl())
                                .build();
                        contactMethod.setProject(newProject); // 프로젝트와 연결
                        return contactMethod;
                    })
                    .collect(Collectors.toList());

            newProject.setContactMethodList(contactMethods);
        }

        return projectRepository.save(newProject);
    }


    // 프로젝트 모집글 수정
    @Override
    @Transactional
    public Project updateProject(ProjectRequestDTO.UpdateProjectDTO request, Long projectId) {

        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectHandler(ErrorStatus.PROMOTION_PROJECT_NOT_FOUND));

        existingProject.setTitle(request.getTitle());
        existingProject.setIntro(request.getInfo());
        existingProject.setDescription(request.getDescription());
        existingProject.setCurrent_progress(request.getCurrent_progress());
        existingProject.setSkill(request.getSkill());
        existingProject.setPart(request.getPart());
        existingProject.setRecruitNum(request.getRecruitNum());
        existingProject.setDev_stack(request.getDev_stack());
        existingProject.setPm_stack(request.getPm_stack());
        existingProject.setDesign_stack(request.getDesign_stack());

        projectMemberRepository.deleteByProject(existingProject);

        // 팀원 추가
        List<Member> memberList = request.getProjectMember().stream()
                .map(memberId -> memberRepository.findById(memberId)
                        .orElseThrow(() -> new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_ID_NOT_FOUND)))
                .collect(Collectors.toList());

        List<ProjectMember> newMembers = ProjectMemberConverter.toProjectMemberList(memberList);
        newMembers.forEach(projectMember -> {projectMember.setProject(existingProject);});

        existingProject.setProjectMemberList(newMembers);

        projectCategoryPreferRepository.deleteByProject(existingProject);

        // 카테고리 추가
        List<ProjectCategory> projectCategoryList = request.getProjectCategoryPrefer().stream()
                .map(projectCategoryId -> projectCategoryRepository.findById(projectCategoryId)
                        .orElseThrow(() -> new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_ID_NOT_FOUND)))
                .collect(Collectors.toList());

        List<ProjectCategoryPrefer> newCategoryPrefers = ProjectCategoryPreferConverter.toProjectCategoryPreferList(projectCategoryList);
        newCategoryPrefers.forEach(projectCategoryPrefer -> {projectCategoryPrefer.setProject(existingProject);});

        existingProject.setProjectCategoryPreferList(newCategoryPrefers);

        return projectRepository.save(existingProject);
    }

    // 프로젝트 모집글 삭제
    public Void deleteProject(Long projectId){
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_NOT_FOUND));

        projectRepository.deleteById(existingProject.getId());
        return null;
    }
}
