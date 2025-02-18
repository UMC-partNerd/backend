package com.partnerd.repository.communityCommentRepository;


public interface CommunityCommentLikesRepositoryCustom {
    
    // 커뮤니티 댓글 좋아요 여부 확인
    boolean existsByMemberIdAndCommunityCommentId(Long memberId, Long commentId);
    
    // 커뮤니티 댓글 좋아요 취소
    void deleteByMemberIdAndCommunityCommentId(Long memberId, Long commentId);
}
