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
}
