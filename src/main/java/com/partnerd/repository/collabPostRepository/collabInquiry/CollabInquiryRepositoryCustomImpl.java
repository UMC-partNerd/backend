package com.partnerd.repository.collabPostRepository.collabInquiry;

import com.partnerd.mongoRepository.domain.CollabInquiry;
import com.partnerd.domain.QCollabInquiry;
import com.partnerd.domain.QCollabPost;
import com.partnerd.domain.QMember;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CollabInquiryRepositoryCustomImpl implements CollabInquiryRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final  QCollabInquiry qCollabInquiry = QCollabInquiry.collabInquiry;
    private final QMember qMember = QMember.member;
    private final QCollabPost qCollabPost = QCollabPost.collabPost;

    @Override
    public Optional<CollabInquiry> findByIdWithMemberAndPost(Long id) {

        JPAQuery<CollabInquiry> query = queryFactory
                .selectFrom(qCollabInquiry)
                .leftJoin(qCollabInquiry.member, qMember)
                .fetchJoin()
                .leftJoin(qCollabInquiry.collabPost, qCollabPost)
                .fetchJoin()
                .where(qCollabInquiry.id.eq(id));

        return Optional.ofNullable(query.fetchOne());
    }
}

