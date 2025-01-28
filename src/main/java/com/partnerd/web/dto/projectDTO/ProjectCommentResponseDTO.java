package com.partnerd.web.dto.projectDTO;

import lombok.*;

public class ProjectCommentResponseDTO {

    // 프로젝트 댓글/대댓글 생성/수정
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
}
