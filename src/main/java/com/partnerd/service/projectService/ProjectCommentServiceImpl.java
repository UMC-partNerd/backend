package com.partnerd.service.projectService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ProjectHandler;
import com.partnerd.converter.projectConverter.ProjectCommentConverter;
import com.partnerd.domain.ProjectComment;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.projectRepository.ProjectCommentRepository;
import com.partnerd.repository.projectRepository.ProjectRepository;
import com.partnerd.web.dto.projectDTO.ProjectCommentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectCommentServiceImpl implements ProjectCommentService {

    private final ProjectRepository projectRepository;
    private final ProjectCommentRepository projectCommentRepository;
    private final MemberRepository memberRepository;

    // 모집 프로젝트 댓글 작성
    @Override
    @Transactional
    public ProjectComment addProjectComment(Long memberId, Long projectId, ProjectCommentRequestDTO.AddProjectCommentDTO request){

        ProjectComment projectComment = ProjectCommentConverter.toProjectComment(request);

        projectComment.setProject(projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_NOT_FOUND)));

        projectComment.setMember(memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ProjectHandler(ErrorStatus.MEMBER_NOT_FOUND)));

        return projectCommentRepository.save(projectComment);
    }

    // 모집 프로젝트 댓글 작성
    @Override
    @Transactional
    public ProjectComment addChildProjectComment(Long memberId, Long projectId, Long parentId, ProjectCommentRequestDTO.AddProjectCommentDTO request){

        ProjectComment projectComment = ProjectCommentConverter.toProjectComment(request);

        if (parentId != null) {
            ProjectComment parentComment = projectCommentRepository.findById(parentId).orElseThrow(() ->
                    new ProjectHandler(ErrorStatus.RECRUIT_PARENT_PROJECT_COMMENT_NOT_FOUND));

            projectComment.addParentComment(parentComment);

            projectComment.setProject(projectRepository.findById(projectId)
                    .orElseThrow(() ->
                            new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_NOT_FOUND)));

            projectComment.setMember(memberRepository.findById(memberId)
                    .orElseThrow(() ->
                            new ProjectHandler(ErrorStatus.MEMBER_NOT_FOUND)));
        } else {
            throw new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_ID_NOT_FOUND);
        }

        return projectCommentRepository.save(projectComment);
    }
}
