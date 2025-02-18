package com.partnerd.service.projectService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ProjectHandler;
import com.partnerd.converter.projectConverter.ProjectCommentConverter;
import com.partnerd.domain.Member;
import com.partnerd.domain.ProjectComment;
import com.partnerd.domain.mapping.ProjectLikes;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.projectRepository.projectComment.ProjectCommentRepository;
import com.partnerd.repository.projectRepository.projectLikes.ProjectLikesRepository;
import com.partnerd.repository.projectRepository.project.ProjectRepository;
import com.partnerd.service.projectService.ProjectCommentService;
import com.partnerd.web.dto.projectDTO.ProjectCommentRequestDTO;
import com.partnerd.web.dto.projectDTO.ProjectCommentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectCommentServiceImpl implements ProjectCommentService {

    private final ProjectRepository projectRepository;
    private final ProjectCommentRepository projectCommentRepository;
    private final MemberRepository memberRepository;
    private final ProjectLikesRepository projectLikesRepository;

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

        projectComment.setLikes(0L);

        return projectCommentRepository.save(projectComment);
    }

    // 모집 프로젝트 대댓글 작성
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

        projectComment.setLikes(0L);
        return projectCommentRepository.save(projectComment);
    }

    // 모집 프로젝트 댓글/대댓글 수정
    @Override
    @Transactional
    public ProjectComment updateProjectComment(Long memberId, Long commentId, ProjectCommentRequestDTO.AddProjectCommentDTO request){

        memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ProjectHandler(ErrorStatus.MEMBER_NOT_FOUND));

        ProjectComment projectComment = projectCommentRepository.findById(commentId)
                .orElseThrow(() ->
                        new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_COMMENT_NOT_FOUND));

        // 작성자 검증
        if (!projectComment.getMember().getId().equals(memberId))
            throw new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_COMMENT_NOT_AUTHOR);

        projectComment.setContents(request.getContents());

        return projectCommentRepository.save(projectComment);
    }

    // 모집 프로젝트 댓글/대댓글 삭제
    @Override
    @Transactional
    public void deleteProjectComment(Long memberId, Long commentId){
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ProjectHandler(ErrorStatus.MEMBER_NOT_FOUND));

        ProjectComment projectComment = projectCommentRepository.findById(commentId)
                .orElseThrow(() -> new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_COMMENT_NOT_FOUND));

        // 작성자 검증
        if (!projectComment.getMember().getId().equals(memberId))
            throw new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_COMMENT_NOT_AUTHOR);

        ProjectComment parentComment = projectComment.getParentComment();

        if (parentComment == null){  // 부모 댓글일때
            if (!projectComment.getChildren().isEmpty()){
                projectComment.changeToDeleted();
            } else {
                projectCommentRepository.delete(projectComment);
            }
        } else {    // 자식 댓글일 때
            if (parentComment.getIsDeleted() && projectComment.getParentComment().getChildren().size() == 1){
                projectCommentRepository.delete(projectComment.getParentComment());
            } else {
                parentComment.getChildren().remove(projectComment);
                projectCommentRepository.delete(projectComment);
            }
        }
    }

    // 프로젝트 모집 댓글 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<ProjectCommentResponseDTO.GetProjectCommentListResultDTO> getProjectCommentList(Long projectId){
        List<ProjectComment> projectCommentList = projectCommentRepository.findProjectCommentList(projectId);

        return projectCommentList.stream()
                .map(ProjectCommentConverter::toGetProjectCommentListResultDTO)
                .toList();
    }

    // 프로젝트 모집 댓글 좋아요
    @Override
    public void projectLikes(Long memberId, Long commentId){
        boolean exists = projectLikesRepository.existsByMemberIdAndProjectCommentId(memberId, commentId);
        ProjectComment comment = projectCommentRepository.findById(commentId)
                .orElseThrow(() -> new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_COMMENT_NOT_FOUND));


        if (exists) {
            // 좋아요 취소
            projectLikesRepository.deleteByMemberIdAndProjectCommentId(memberId, commentId);
            comment.setLikes(comment.getLikes() - 1);
        } else {
            // 좋아요 추가
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new ProjectHandler(ErrorStatus.MEMBER_NOT_FOUND));

            ProjectLikes projectLikes = ProjectLikes.builder()
                    .member(member)
                    .projectComment(comment)
                    .build();

            projectLikesRepository.save(projectLikes);
            comment.setLikes(comment.getLikes() + 1);
        }

        projectCommentRepository.save(comment);
    }
}
