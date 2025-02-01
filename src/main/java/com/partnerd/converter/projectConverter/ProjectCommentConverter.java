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
                .profile_url(null)  // 임시 이미지 (추후 리팩)
                .build();
    }

    // 프로젝트 모집 댓글 전체 조회
    public static ProjectCommentResponseDTO.GetProjectCommentListResultDTO toGetProjectCommentListResultDTO(ProjectComment projectComment) {
        return ProjectCommentResponseDTO.GetProjectCommentListResultDTO.builder()
                .projectCommentId(projectComment.getId())
                .isDeleted(projectComment.getIsDeleted())
                .nickname(projectComment.getMember().getNickname())
                .profile_url(null)  // 임시 이미지 (추후 리팩)
                .contents(projectComment.getContents())
                .likes(projectComment.getLikes())
                .createdAt(projectComment.getCreatedAt())
                .updatedAt(projectComment.getUpdatedAt())
                .children(
                        projectComment.getChildren().stream()
                                .map(ProjectCommentConverter::toGetProjectCommentListResultDTO)
                                .toList()
                )
                .build();
    }
}
