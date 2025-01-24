package com.partnerd.repository.projectRepository;


import com.partnerd.domain.PromotionProject;
import com.partnerd.domain.QContactMethod;
import com.partnerd.domain.QPromotionProject;
import com.partnerd.domain.mapping.QPromotionProjectMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
