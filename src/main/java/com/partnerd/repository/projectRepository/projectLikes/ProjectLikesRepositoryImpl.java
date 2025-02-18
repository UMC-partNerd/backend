package com.partnerd.repository.projectRepository.projectLikes;


import com.partnerd.domain.mapping.QProjectLikes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@RequiredArgsConstructor
public class ProjectLikesRepositoryImpl implements ProjectLikesRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final QProjectLikes qProjectLikes = QProjectLikes.projectLikes;

    // 프로젝트 모집 댓글 좋아요 여부 확인
    @Override
    public boolean existsByMemberIdAndProjectCommentId(Long memberId, Long commentId) {
        return queryFactory
                .selectOne()
                .from(qProjectLikes)
                .where(qProjectLikes.member.id.eq(memberId)
                        .and(qProjectLikes.projectComment.id.eq(commentId)))
                .fetchFirst() != null;
    }

    // 프로젝트 모집 댓글 좋아요 취소
    @Override
    @Transactional
    public void deleteByMemberIdAndProjectCommentId(Long memberId, Long commentId) {
        queryFactory
                .delete(qProjectLikes)
                .where(qProjectLikes.member.id.eq(memberId)
                        .and(qProjectLikes.projectComment.id.eq(commentId)))
                .execute();
    }

    // 프로젝트 홍보 댓글 좋아요 여부 확인
    @Override
    public boolean existsByMemberIdAndPromotionProjectCommentId(Long memberId, Long commentId) {
        return queryFactory
                .selectOne()
                .from(qProjectLikes)
                .where(qProjectLikes.member.id.eq(memberId)
                        .and(qProjectLikes.promotionProjectComment.id.eq(commentId)))
                .fetchFirst() != null;
    }

    // 프로젝트 홍보 댓글 좋아요 취소
    @Override
    @Transactional
    public void deleteByMemberIdAndPromotionProjectCommentId(Long memberId, Long commentId) {
        queryFactory
                .delete(qProjectLikes)
                .where(qProjectLikes.member.id.eq(memberId)
                        .and(qProjectLikes.promotionProjectComment.id.eq(commentId)))
                .execute();
    }
}
