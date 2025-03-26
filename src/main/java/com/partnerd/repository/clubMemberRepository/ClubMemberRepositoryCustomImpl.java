package com.partnerd.repository.clubMemberRepository;

import com.partnerd.domain.QClub;
import com.partnerd.domain.QMember;
import com.partnerd.mongoRepository.domain.mapping.ClubMember;
import com.partnerd.domain.mapping.QClubMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ClubMemberRepositoryCustomImpl implements ClubMemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QClubMember qClubMember = QClubMember.clubMember;
    private final QMember qMember = QMember.member;
    private final QClub qClub = QClub.club;

    @Override
    public Optional<ClubMember> findByClubIdAndMemberId(Long clubId, Long memberId) {
        ClubMember result = queryFactory
                .selectFrom(qClubMember)
                .leftJoin(qClubMember.club, qClub).fetchJoin()      // Club 관계 조인
                .leftJoin(qClubMember.member, qMember).fetchJoin()  // Member 관계 조인
                .where(
                        qClub.id.eq(clubId),                       // 클럽 ID 조건
                        qMember.id.eq(memberId)                    // 멤버 ID 조건
                )
                .fetchOne();                                       // 단일 결과 조회

        return Optional.ofNullable(result);
    }
}