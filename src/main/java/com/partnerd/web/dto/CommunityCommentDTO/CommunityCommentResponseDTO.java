package com.partnerd.web.dto.CommunityCommentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CommunityCommentResponseDTO {
    
    // 커뮤니티 댓글/대댓글 생성/수정
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddCommunityCommentResultDTO {
        private Long communityCommentId;     // 댓글 id
        private String nickname;    // 작성자 닉네임
        private String profileKeyName; // 프로필 사진
        private String contents;    // 내용
    }

    // 커뮤니티 댓글 전체 조회
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCommunityCommentListResultDTO {
        private Long communityCommentId;    // 댓글 ID
        private Boolean isDeleted;      // 삭제됨 여부
        private String nickname;          // 작성자 닉네임
        private String profileKeyName;       // 프로필 사진
        private String contents;          // 내용
        private Long likes;                // 좋아요 수
        private LocalDateTime createdAt; // 작성일
        private LocalDateTime updatedAt; // 수정일
        private List<GetCommunityCommentListResultDTO> children; // 대댓글 리스트
    }
}
