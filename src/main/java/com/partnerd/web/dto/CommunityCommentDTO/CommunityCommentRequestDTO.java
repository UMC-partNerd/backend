package com.partnerd.web.dto.CommunityCommentDTO;

import lombok.Getter;
import lombok.Setter;

public class CommunityCommentRequestDTO {

    // 커뮤니티 댓글/대댓글 생성/수정
    @Getter
    @Setter
    public static class AddCommunityCommentDTO {
        private String contents;
    }
}
