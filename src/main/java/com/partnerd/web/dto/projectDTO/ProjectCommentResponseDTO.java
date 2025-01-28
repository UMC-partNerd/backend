package com.partnerd.web.dto.projectDTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ProjectCommentResponseDTO {

    // 프로젝트 모집 댓글/대댓글 생성/수정
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddProjectCommentResultDTO {
        private Long projectCommentId;     // 프로젝트 댓글 id
        private String nickname;    // 작성자 닉네임
        private String profile_url; // 프로필 사진
        private String contents;    // 내용
    }
    
    // 프로젝트 모집 댓글 전체 조회
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetProjectCommentListResultDTO {
        private Long projectCommentId;    // 댓글 ID
        private String nickname;          // 작성자 닉네임
        private String profile_url;       // 프로필 사진
        private String contents;          // 내용
        private int likes;                // 좋아요 수
        private LocalDateTime createdAt; // 작성일
        private LocalDateTime updatedAt; // 수정일
        private List<GetProjectCommentListResultDTO> children; // 대댓글 리스트
    }
}
