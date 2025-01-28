package com.partnerd.web.dto.projectDTO;

import lombok.Getter;
import lombok.Setter;

public class ProjectCommentRequestDTO {

    // 프로젝트 댓글/대댓글 생성/수정
    @Getter
    @Setter
    public static class AddProjectCommentDTO {
        private String contents;
    }
}
