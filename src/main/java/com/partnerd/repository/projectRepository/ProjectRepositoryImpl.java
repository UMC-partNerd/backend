package com.partnerd.repository.projectRepository;


import com.partnerd.domain.Project;
import com.partnerd.domain.QContactMethod;
import com.partnerd.domain.QProject;
import com.partnerd.domain.mapping.QProjectCategoryPrefer;
import com.partnerd.domain.mapping.QProjectMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustiom{

    private final JPAQueryFactory queryFactory;
    private final QProject qProject = QProject.project;
    private final QContactMethod qContactMethod = QContactMethod.contactMethod;
    private final QProjectMember qProjectMember = QProjectMember.projectMember;
    private final QProjectCategoryPrefer qProjectCategoryPrefer = QProjectCategoryPrefer.projectCategoryPrefer;

    // 프로젝트 모집글 상세페이지 조회
    @Override
    public Project findProjectDetails(Long projectId){
        return queryFactory
                .selectFrom(qProject)
                .leftJoin(qProject.contactMethodList, qContactMethod).fetchJoin()
                .leftJoin(qProject.projectMemberList, qProjectMember).fetchJoin()
                .leftJoin(qProject.projectCategoryPreferList, qProjectCategoryPrefer).fetchJoin()
                .where(qProject.id.eq(projectId))
                .fetchOne();
    }

    // 마이페이지 - 내가 쓴 프로젝트 모집글 모아보기
    @Override
    public List<Project> findProjectsByMemberId(Long memberId){
        return queryFactory
                .selectFrom(qProject)
                .leftJoin(qProject.projectMemberList, qProjectMember).fetchJoin()
                .where(qProjectMember.member.id.eq(memberId))
                .distinct()
                .fetch();
    }
}
