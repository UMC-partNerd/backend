package com.partnerd.repository.projectRepository;


import com.partnerd.domain.PromotionProject;
import com.partnerd.domain.QContactMethod;
import com.partnerd.domain.QPromotionProject;
import com.partnerd.domain.mapping.QPromotionProjectMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PromotionProjectRepositoryImpl implements PromotionProjectRepositoryCustiom{

    private final JPAQueryFactory queryFactory;
    private final QPromotionProject qPromotionProject = QPromotionProject.promotionProject;
    private final QContactMethod qContactMethod = QContactMethod.contactMethod;
    private final QPromotionProjectMember qPromotionProjectMember = QPromotionProjectMember.promotionProjectMember;

    // 프로젝트 홍보글 상세페이지 조회
    @Override
    @Transactional
    public PromotionProject findPromotionProjectDetails(Long promotionProjectId){
        queryFactory
                .update(qPromotionProject)
                .set(qPromotionProject.views, qPromotionProject.views.add(1))
                .where(qPromotionProject.id.eq(promotionProjectId))
                .execute();

        return queryFactory
                .selectFrom(qPromotionProject)
                .leftJoin(qPromotionProject.contactMethodList, qContactMethod).fetchJoin()
                .leftJoin(qPromotionProject.promotionProjectMemberList, qPromotionProjectMember).fetchJoin()
                .where(qPromotionProject.id.eq(promotionProjectId))
                .fetchOne();
    }


    // 마이페이지 - 내가 쓴 프로젝트 홍보글 모아보기
    @Override
    public List<PromotionProject> findPromotionProjectsByMemberId(Long memberId){
        return queryFactory
                .selectFrom(qPromotionProject)
                .leftJoin(qPromotionProject.promotionProjectMemberList, qPromotionProjectMember).fetchJoin()
                .where(qPromotionProjectMember.member.id.eq(memberId))
                .distinct()
                .fetch();
    }
}
