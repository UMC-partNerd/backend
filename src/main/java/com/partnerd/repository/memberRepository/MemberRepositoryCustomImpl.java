package com.partnerd.repository.memberRepository;

import com.partnerd.mongoRepository.domain.Member;
import com.partnerd.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findByNicknameContaining(String nickname) {
        QMember member = QMember.member;

        return queryFactory
                .selectFrom(member)
                .where(member.nickname.contains(nickname)) // LIKE '%닉네임%'
                .orderBy(member.nickname.asc()) // 닉네임 오름차순 정렬
                .fetch();
    }
}
