package com.partnerd.converter.projectConverter;

import com.partnerd.domain.ProjectComment;
import com.partnerd.web.dto.projectDTO.ProjectCommentRequestDTO;
import com.partnerd.web.dto.projectDTO.ProjectCommentResponseDTO;

public class ProjectCommentConverter {

    // 프로젝트 댓글/대댓글 생성/수정
    public static ProjectComment toProjectComment (ProjectCommentRequestDTO.AddProjectCommentDTO request){
        return ProjectComment.builder()
                .contents(request.getContents())
                .isDeleted(false)
                .build();
    }

    public static ProjectCommentResponseDTO.AddProjectCommentResultDTO toAddProjectCommentResultDTO (ProjectComment projectComment) {

        return ProjectCommentResponseDTO.AddProjectCommentResultDTO.builder()
                .projectCommentId(projectComment.getId())
                .contents(projectComment.getContents())
                .nickname(projectComment.getMember().getNickname())
                .profile_url(projectComment.getMember().getProfile_url())
                .build();
    }
}
