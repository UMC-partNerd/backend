package com.partnerd.service.projectService;

import com.partnerd.domain.ProjectComment;
import com.partnerd.web.dto.projectDTO.ProjectCommentRequestDTO;

public interface ProjectCommentService {

    // 모집 프로젝트 댓글 작성
    ProjectComment addProjectComment(Long memberId, Long projectId, ProjectCommentRequestDTO.AddProjectCommentDTO request);

    // 모집 프로젝트 대댓글 작성
    ProjectComment addChildProjectComment(Long memberId, Long projectId, Long parentId, ProjectCommentRequestDTO.AddProjectCommentDTO request);
}
