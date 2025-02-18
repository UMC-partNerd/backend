package com.partnerd.repository.communityCommentRepository;


import com.partnerd.domain.mapping.QCommunityCommentLikes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@RequiredArgsConstructor
public class CommunityCommentLikesRepositoryImpl implements CommunityCommentLikesRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QCommunityCommentLikes qCommunityCommentLikes = QCommunityCommentLikes.communityCommentLikes;

    // 커뮤니티 댓글 좋아요 여부 확인
    @Override
    public boolean existsByMemberIdAndCommunityCommentId(Long memberId, Long commentId) {
        return queryFactory
                .selectOne()
                .from(qCommunityCommentLikes)
                .where(qCommunityCommentLikes.member.id.eq(memberId)
                        .and(qCommunityCommentLikes.communityComment.id.eq(commentId)))
                .fetchFirst() != null;
    }

    // 커뮤니티 모집 댓글 좋아요 취소
    @Override
    @Transactional
    public void deleteByMemberIdAndCommunityCommentId(Long memberId, Long commentId) {
        queryFactory
                .delete(qCommunityCommentLikes)
                .where(qCommunityCommentLikes.member.id.eq(memberId)
                        .and(qCommunityCommentLikes.communityComment.id.eq(commentId)))
                .execute();
    }

}
