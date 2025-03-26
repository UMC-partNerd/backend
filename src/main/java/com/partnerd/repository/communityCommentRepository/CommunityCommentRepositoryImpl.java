package com.partnerd.repository.communityCommentRepository;

import com.partnerd.mongoRepository.domain.CommunityComment;
import com.partnerd.domain.QCommunityComment;
import com.partnerd.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CommunityCommentRepositoryImpl implements CommunityCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QCommunityComment qCommunityComment = QCommunityComment.communityComment;

    private final QMember qMember = QMember.member;

    // 커뮤니티 댓글 전체 조회
    @Override
    public List<CommunityComment> findCommunityCommentList(Long communityId){
        return queryFactory.selectFrom(qCommunityComment)
                .leftJoin(qCommunityComment.children).fetchJoin()
                .leftJoin(qCommunityComment.member, qMember).fetchJoin()
                .where(
                        qCommunityComment.community.id.eq(communityId)
                                .and(qCommunityComment.parentComment.isNull())
                )
                .orderBy(qCommunityComment.createdAt.asc())
                .fetch();
    }
}
