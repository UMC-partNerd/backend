package com.partnerd.repository.projectRepository;



import com.partnerd.domain.Project;
import com.partnerd.domain.QContactMethod;
import com.partnerd.domain.QProject;
import com.partnerd.domain.QProjectImage;
import com.partnerd.domain.enums.ImageType;
import com.partnerd.domain.mapping.QProjectCategoryPrefer;
import com.partnerd.domain.mapping.QProjectMember;
import com.partnerd.web.dto.homeDTO.response.HomeProjectDTO;
import com.querydsl.core.types.Projections;
import com.partnerd.domain.*;
import com.partnerd.web.dto.projectDTO.ProjectRequestDTO;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustiom{

    private final JPAQueryFactory queryFactory;
    private final QProject qProject = QProject.project;
    private final QContactMethod qContactMethod = QContactMethod.contactMethod;
    private final QProjectMember qProjectMember = QProjectMember.projectMember;
    private final QProjectCategoryPrefer qProjectCategoryPrefer = QProjectCategoryPrefer.projectCategoryPrefer;

    private final QMember qMember = QMember.member;

    // 프로젝트 팀원 검색
    @Override
    public List<Member> getMemberForProject(ProjectRequestDTO.FindProjectMemberDTO request) {
        String keyword = request.getKeyword();

        return queryFactory
                .selectFrom(qMember)
                .where(qMember.nickname.contains(keyword))
                .fetch();
    }

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

    // 프로젝트 모집글 모아보기
    @Override
    public Page<Project> getProjectList(Integer page, Integer status, List<Long> category, String keyword){

        Pageable pageable = PageRequest.of(page, 16);

        JPQLQuery<Project> query = queryFactory.selectFrom(qProject);

        Date now = new Date();

        // 모집 상태 필터링
        if (status != null) {
            switch (status) {
                case 0:
                    query.where(qProject.endDate.goe(now));
                    break;
                case 1:
                    query.where(qProject.endDate.lt(now));
                    break;
            }
        }

        // 카테고리 필터링
        if (category != null && !category.isEmpty()) {
            query.where(qProject.projectCategoryPreferList.any().projectCategory.id.in(category));
        }

        // 검색 필터링
        if (keyword != null && !keyword.isEmpty()) {
            query.where(qProject.title.containsIgnoreCase(keyword));
        }

        query.orderBy(qProject.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Project> content = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);
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

    // 홈화면 - 최신 프로젝트 조회
    @Override
    public List<HomeProjectDTO> findTopProjects(Pageable pageable) {
        QProject project = QProject.project;
        QProjectImage projectImage = QProjectImage.projectImage;

        return queryFactory
                .select(Projections.constructor(HomeProjectDTO.class,
                        projectImage.keyName,
                        project.title,
                        project.intro))
                .from(project)
                .leftJoin(project.projectImageList, projectImage)
                .on(projectImage.imageType.eq(ImageType.THUMBNAIL))
                .orderBy(project.createdAt.desc())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
