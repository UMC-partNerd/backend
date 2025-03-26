package com.partnerd.service.projectService;

import com.partnerd.mongoRepository.domain.ProjectComment;
import com.partnerd.web.dto.projectDTO.ProjectCommentRequestDTO;
import com.partnerd.web.dto.projectDTO.ProjectCommentResponseDTO;

import java.util.List;

public interface ProjectCommentService {

    // 모집 프로젝트 댓글 작성
    ProjectComment addProjectComment(Long memberId, Long projectId, ProjectCommentRequestDTO.AddProjectCommentDTO request);

    // 모집 프로젝트 대댓글 작성
    ProjectComment addChildProjectComment(Long memberId, Long projectId, Long parentId, ProjectCommentRequestDTO.AddProjectCommentDTO request);

    // 모집 프로젝트 댓글/대댓글 수정
    ProjectComment updateProjectComment(Long memberId, Long commentId, ProjectCommentRequestDTO.AddProjectCommentDTO request);

    // 모집 프로젝트 댓글/대댓글 삭제
    void deleteProjectComment(Long memberId, Long commentId);

    // 프로젝트 모집 댓글 전체 조회
    List<ProjectCommentResponseDTO.GetProjectCommentListResultDTO> getProjectCommentList(Long projectId);

    // 프로젝트 모집 댓글 좋아요
    void projectLikes(Long memberId, Long commentId);
}
