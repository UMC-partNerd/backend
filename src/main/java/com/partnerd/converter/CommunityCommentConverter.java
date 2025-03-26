package com.partnerd.converter;


import com.partnerd.mongoRepository.domain.CommunityComment;
import com.partnerd.web.dto.CommunityCommentDTO.CommunityCommentRequestDTO;
import com.partnerd.web.dto.CommunityCommentDTO.CommunityCommentResponseDTO;

public class CommunityCommentConverter {
    // 커뮤니티 댓글/대댓글 생성/수정
    public static CommunityComment toCommunityComment (CommunityCommentRequestDTO.AddCommunityCommentDTO request){
        return CommunityComment.builder()
                .contents(request.getContents())
                .isDeleted(false)
                .build();
    }

    public static CommunityCommentResponseDTO.AddCommunityCommentResultDTO toAddCommunityCommentResultDTO (CommunityComment communityComment) {

        return CommunityCommentResponseDTO.AddCommunityCommentResultDTO.builder()
                .communityCommentId(communityComment.getId())
                .contents(communityComment.getContents())
                .nickname(communityComment.getMember().getNickname())
                .profileKeyName(communityComment.getMember().getProfile_url())
                .build();
    }

    // 커뮤니티 댓글 전체 조회
    public static CommunityCommentResponseDTO.GetCommunityCommentListResultDTO toGetCommunityCommentListResultDTO(CommunityComment communityComment) {
        return CommunityCommentResponseDTO.GetCommunityCommentListResultDTO.builder()
                .communityCommentId(communityComment.getId())
                .isDeleted(communityComment.getIsDeleted())
                .nickname(communityComment.getMember().getNickname())
                .profileKeyName(communityComment.getMember().getProfile_url())
                .contents(communityComment.getContents())
                .likes(communityComment.getLikes())
                .createdAt(communityComment.getCreatedAt())
                .updatedAt(communityComment.getUpdatedAt())
                .children(
                        communityComment.getChildren().stream()
                                .map(CommunityCommentConverter::toGetCommunityCommentListResultDTO)
                                .toList()
                )
                .build();
    }
}
