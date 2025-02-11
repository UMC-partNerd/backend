package com.partnerd.repository.projectRepository;


import com.partnerd.domain.mapping.QProjectVote;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class ProjectVoteRepositoryImpl implements ProjectVoteRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private final QProjectVote qProjectVote = QProjectVote.projectVote;

    // 프로젝트 홍보 투표하기 여부 확인
    @Override
    public boolean existsByMemberIdAndPromotionProjectId(Long memberId, Long promotionProjectId) {
        return queryFactory
                .selectOne()
                .from(qProjectVote)
                .where(qProjectVote.member.id.eq(memberId)
                        .and(qProjectVote.promotionProject.id.eq(promotionProjectId)))
                .fetchFirst() != null;
    }

}
