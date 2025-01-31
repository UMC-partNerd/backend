package com.partnerd.repository.clubMembershipRequestRepository;

import com.partnerd.domain.mapping.ClubMembershipRequest;
import com.partnerd.domain.mapping.QClubMembershipRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ClubMembershipRequestRepositoryCustomImpl implements ClubMembershipRequestRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QClubMembershipRequest qClubMembershipRequest = QClubMembershipRequest.clubMembershipRequest;

    @Override
    public Optional<ClubMembershipRequest> findByRequest(Long memberId, Long clubId, Long requestId) {
        ClubMembershipRequest result = queryFactory
                .selectFrom(qClubMembershipRequest)
                .where(
                        qClubMembershipRequest.member.id.eq(memberId),   // memberId 조건
                        qClubMembershipRequest.club.id.eq(clubId),       // clubId 조건
                        qClubMembershipRequest.id.eq(requestId)          // requestId 조건
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ClubMembershipRequest> findByMemberIdAndClubId(Long memberId, Long clubId) {
        ClubMembershipRequest result = queryFactory
                .selectFrom(qClubMembershipRequest)
                .where(
                        qClubMembershipRequest.member.id.eq(memberId),  // 특정 멤버 ID
                        qClubMembershipRequest.club.id.eq(clubId)       // 특정 클럽 ID
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}