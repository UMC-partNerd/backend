package com.partnerd.repository.communityRepository.impl;

import com.partnerd.domain.Community;
import com.partnerd.domain.QCommunity;
import com.partnerd.domain.QMember;
import com.partnerd.repository.communityRepository.CommunityRepositoryCustom;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityRepositoryCustomImpl implements CommunityRepositoryCustom {


    private final JPAQueryFactory jpaQuery;
    private final QCommunity qCommunity = QCommunity.community;
    private final QMember qMember = QMember.member;
    @Override
    public List<Community> findByCursorPagingWithMember(Long cursorId, Pageable pageable) {

        JPAQuery<Community> query = jpaQuery
                .selectFrom(qCommunity)
                .leftJoin(qCommunity.member, qMember).fetchJoin()
                .orderBy(qCommunity.id.desc())
                .limit(pageable.getPageSize());

        if(cursorId != null) {
            query.where(qCommunity.id.eq(cursorId));
        }

        return query.fetch();
    }

    @Override
    public List<Community> findTop10ByLikes() {

        return jpaQuery.selectFrom(qCommunity)
                .orderBy(qCommunity.likes.desc())
                .limit(10)
                .fetch();

    }
}
