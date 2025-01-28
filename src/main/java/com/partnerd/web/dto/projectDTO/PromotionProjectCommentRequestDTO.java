package com.partnerd.web.dto.projectDTO;

import lombok.Getter;
import lombok.Setter;

public class PromotionProjectCommentRequestDTO {

    // 프로젝트 홍보글 댓글/대댓글 생성/수정
    @Getter
    @Setter
    public static class AddPromotionProjectCommentDTO {
        private String contents;
    }
}
