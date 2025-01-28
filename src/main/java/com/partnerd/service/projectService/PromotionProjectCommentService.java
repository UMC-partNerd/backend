package com.partnerd.service.projectService;

import com.partnerd.domain.PromotionProjectComment;
import com.partnerd.web.dto.projectDTO.PromotionProjectCommentRequestDTO;

public interface PromotionProjectCommentService {

    // 프로젝트 홍보글 댓글 작성
    PromotionProjectComment addPromotionProjectComment(Long memberId, Long projectId, PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request);
}
