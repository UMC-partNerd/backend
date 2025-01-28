package com.partnerd.converter.projectConverter;

import com.partnerd.domain.PromotionProjectComment;
import com.partnerd.web.dto.projectDTO.PromotionProjectCommentRequestDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectCommentResponseDTO;

public class PromotionProjectCommentConverter {

    // 프로젝트 홍보글 댓글/대댓글 생성/수정
    public static PromotionProjectComment toPromotionProjectComment(PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request){
        return PromotionProjectComment.builder()
                .contents(request.getContents())
                .isDeleted(false)
                .build();
    }

    public static PromotionProjectCommentResponseDTO.AddPromotionProjectCommentResultDTO toAddPromotionProjectCommentResultDTO(PromotionProjectComment promotionProjectComment){
        return PromotionProjectCommentResponseDTO.AddPromotionProjectCommentResultDTO.builder()
                .promotionProjectCommentId(promotionProjectComment.getId())
                .contents(promotionProjectComment.getContents())
                .nickname(promotionProjectComment.getMember().getNickname())
                .profile_url(promotionProjectComment.getMember().getProfile_url())
                .build();
    }

    // 프로젝트 홍보글 댓글 전체 조회
    public static PromotionProjectCommentResponseDTO.GetPromotionProjectCommentListResultDTO toGetPromotionProjectCommentListResultDTO(PromotionProjectComment promotionProjectComment) {
        return PromotionProjectCommentResponseDTO.GetPromotionProjectCommentListResultDTO.builder()
                .promotionProjectCommentId(promotionProjectComment.getId())
                .isDeleted(promotionProjectComment.getIsDeleted())
                .nickname(promotionProjectComment.getMember().getNickname())
                .profile_url(promotionProjectComment.getMember().getProfile_url())
                .contents(promotionProjectComment.getContents())
                .likes(promotionProjectComment.getLikes())
                .createdAt(promotionProjectComment.getCreatedAt())
                .updatedAt(promotionProjectComment.getUpdatedAt())
                .children(
                        promotionProjectComment.getChildren().stream()
                                .map(PromotionProjectCommentConverter::toGetPromotionProjectCommentListResultDTO)
                                .toList()
                )
                .build();
    }
}
