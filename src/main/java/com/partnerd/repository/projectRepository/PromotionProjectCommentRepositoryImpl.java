package com.partnerd.repository.projectRepository;

import com.partnerd.mongoRepository.domain.PromotionProjectComment;
import com.partnerd.domain.QMember;
import com.partnerd.domain.QPromotionProjectComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PromotionProjectCommentRepositoryImpl implements PromotionProjectCommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QPromotionProjectComment qPromotionProjectComment = QPromotionProjectComment.promotionProjectComment;
    private final QMember qMember = QMember.member;

    // 프로젝트 홍보글 댓글 전체 조회
    @Override
    public List<PromotionProjectComment> findPromotionProjectCommentList(Long projectId) {
        return queryFactory.selectFrom(qPromotionProjectComment)
                .leftJoin(qPromotionProjectComment.children).fetchJoin()
                .leftJoin(qPromotionProjectComment.member, qMember).fetchJoin()
                .where(
                        qPromotionProjectComment.promotionProject.id.eq(projectId)
                                .and(qPromotionProjectComment.parentComment.isNull())
                )
                .orderBy(qPromotionProjectComment.createdAt.asc())
                .fetch();
    }
}
