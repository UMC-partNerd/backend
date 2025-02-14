package com.partnerd.repository.collabAskRepository;

import com.partnerd.domain.QClub;
import com.partnerd.domain.QMember;
import com.partnerd.domain.mapping.CollabAsk;
import com.partnerd.domain.mapping.QClubMember;
import com.partnerd.domain.mapping.QCollabAsk;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CollabAskRepositoryCustomImpl implements CollabAskRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QCollabAsk qCollabAsk = QCollabAsk.collabAsk;
    private final QClubMember qClubMember = QClubMember.clubMember;
    private final QMember qMember = QMember.member;
    private final QClub qClub = QClub.club;



    @Override
    public Page<CollabAsk> findBySenderMemberId(Long senderId, Pageable pageable) {

        JPAQuery<CollabAsk> query = queryFactory
                .selectFrom(qCollabAsk)
                .leftJoin(qCollabAsk.sender, qClubMember)
                .fetchJoin()
                .leftJoin(qClubMember.club, qClub)
                .fetchJoin()
                .leftJoin(qClubMember.member, qMember)
                .fetchJoin()
                .where(qMember.id.eq(senderId));

        long total = query.fetch().size();
        List<CollabAsk> results = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<CollabAsk> findByReceiverMemberId(Long senderId, Pageable pageable) {
        JPAQuery<CollabAsk> query = queryFactory
                .selectFrom(qCollabAsk)
                .leftJoin(qCollabAsk.receiver, qClubMember)
                .fetchJoin()
                .leftJoin(qClubMember.club, qClub)
                .fetchJoin()
                .leftJoin(qClubMember.member, qMember)
                .fetchJoin()
                .where(qMember.id.eq(senderId));

        long total = query.fetch().size();
        List<CollabAsk> results = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        return new PageImpl<>(results, pageable, total);

    }

    @Override
    public Optional<CollabAsk> findByIdWithSenderAndReceiver(Long id) {
        JPAQuery<CollabAsk> query = queryFactory
                .selectFrom(qCollabAsk)
                .leftJoin(qCollabAsk.sender.member, qMember)
                .fetchJoin()
                .leftJoin(qCollabAsk.receiver.member, qMember)
                .fetchJoin()
                .where(qCollabAsk.id.eq(id));

        return Optional.ofNullable(query.fetchOne());
    }
}
