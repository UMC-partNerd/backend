package com.partnerd.service.projectService;

import com.partnerd.domain.ProjectComment;
import com.partnerd.web.dto.projectDTO.ProjectCommentRequestDTO;

public interface ProjectCommentService {

    // 모집 프로젝트 댓글 작성
    ProjectComment addProjectComment(Long memberId, Long projectId, ProjectCommentRequestDTO.AddProjectCommentDTO request);
}
