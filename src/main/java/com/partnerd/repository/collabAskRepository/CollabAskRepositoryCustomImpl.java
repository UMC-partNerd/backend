package com.partnerd.repository.collabAskRepository;

import com.partnerd.domain.QClub;
import com.partnerd.domain.QMember;
import com.partnerd.mongoRepository.domain.mapping.CollabAsk;
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

        QClubMember senderClubMember = new QClubMember("senderClubMember");  // sender ClubMember 별칭 추가
        QClubMember receiverClubMember = new QClubMember("receiverClubMember");  // receiver ClubMember 별칭 추가
        QMember sender = new QMember("sender");  // 별칭 추가
        QMember receiver = new QMember("receiver");  // 별칭 추가

        JPAQuery<CollabAsk> query = queryFactory
                .selectFrom(qCollabAsk)
                .leftJoin(qCollabAsk.sender, senderClubMember).fetchJoin()  // ClubMember 먼저 조인
                .leftJoin(senderClubMember.member, sender).fetchJoin()  // ClubMember 안에서 Member 조인
                .leftJoin(qCollabAsk.receiver, receiverClubMember).fetchJoin()  // ClubMember 먼저 조인
                .leftJoin(receiverClubMember.member, receiver).fetchJoin()  // ClubMember 안에서 Member 조인
                .where(qCollabAsk.id.eq(id));

        return Optional.ofNullable(query.fetchOne());
    }
}
