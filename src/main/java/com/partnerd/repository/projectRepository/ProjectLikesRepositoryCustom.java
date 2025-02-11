package com.partnerd.repository.projectRepository;


import com.partnerd.domain.Member;

public interface ProjectLikesRepositoryCustom {
    
    // 프로젝트 모집 댓글 좋아요 여부 확인
    boolean existsByMemberIdAndProjectCommentId(Long memberId, Long commentId);
    
    // 프로젝트 모집 댓글 좋아요 취소
    void deleteByMemberIdAndProjectCommentId(Long memberId, Long commentId);

    // 프로젝트 홍보 댓글 좋아요 여부 확인
    boolean existsByMemberIdAndPromotionProjectCommentId(Long memberId, Long commentId);

    // 프로젝트 홍보 댓글 좋아요 취소
    void deleteByMemberIdAndPromotionProjectCommentId(Long memberId, Long commentId);
}
