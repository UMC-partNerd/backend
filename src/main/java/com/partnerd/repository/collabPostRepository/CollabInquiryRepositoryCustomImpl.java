/*
package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.CollabInquiry;
import com.partnerd.domain.QCollabInquiry;
import com.partnerd.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CollabInquiryRepositoryCustomImpl implements CollabInquiryRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final  QCollabInquiry collabInquiry = QCollabInquiry.collabInquiry;
    private final QMember member = QMember.member;

    @Override
    public Optional<CollabInquiry> findByIdWithoutParent(Long id) {

        return Optional.empty();
    }
}
*/
