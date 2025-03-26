package com.partnerd.service.projectService;

import com.partnerd.mongoRepository.domain.PromotionProjectComment;
import com.partnerd.web.dto.projectDTO.PromotionProjectCommentRequestDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectCommentResponseDTO;

import java.util.List;

public interface PromotionProjectCommentService {

    // 프로젝트 홍보글 댓글 작성
    PromotionProjectComment addPromotionProjectComment(Long memberId, Long projectId, PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request);

    // 프로젝트 홍보글 대댓글 작성
    PromotionProjectComment addChildPromotionProjectComment(Long memberId, Long projectId, Long parentId, PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request);

    // 프로젝트 홍보글 댓글/대댓글 수정
    PromotionProjectComment updatePromotionProjectComment(Long memberId, Long commentId, PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request);

    // 프로젝트 홍보글 댓글/대댓글 삭제
    void deletePromotionProjectComment(Long memberId, Long commentId);

    // 프로젝트 홍보글 댓글 전체 조회
    List<PromotionProjectCommentResponseDTO.GetPromotionProjectCommentListResultDTO> getPromotionProjectCommentList(Long projectId);

    // 프로젝트 홍보 댓글 좋아요
    void projectLikes(Long memberId, Long commentId);
}
