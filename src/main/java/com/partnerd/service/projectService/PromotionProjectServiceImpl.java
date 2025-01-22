package com.partnerd.service.projectService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.PromotionProjectHandler;
import com.partnerd.converter.projectConverter.PromotionProjectMemberConverter;
import com.partnerd.converter.projectConverter.PromotionProjectConverter;
import com.partnerd.domain.*;
import com.partnerd.domain.mapping.PromotionProjectMember;
import com.partnerd.repository.projectRepository.PromotionProjectMemberRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.projectRepository.PromotionProjectRepository;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionProjectServiceImpl implements PromotionProjectService {

    private final PromotionProjectRepository promotionProjectRepository;
    private final PromotionProjectMemberRepository promotionProjectMemberRepository;
    private final MemberRepository memberRepository;
    private final JPAQueryFactory queryFactory;

    // 프로젝트 홍보 생성
    @Override
    public PromotionProject addPromotionProject(PromotionProjectRequestDTO.CreatePromotionProjectDTO request) {
        PromotionProject newPromotionProject = PromotionProjectConverter.toPromotionProject(request);

        Set<Member> memberList = request.getPromotionProjectMember().stream()
                .map(memberId -> memberRepository.findById(memberId)
                        .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.MEMBER_NOT_FOUND)))
                .collect(Collectors.toSet());

        Set<PromotionProjectMember> promotionProjectMemberList = PromotionProjectMemberConverter.toPromotionProjectMemberList(memberList);

        promotionProjectMemberList.forEach(promotionProjectMember -> {promotionProjectMember.setPromotionProject(newPromotionProject);});

        // 컨택트 방식
        if (request.getContactMethod() != null) {
            Set<ContactMethod> contactMethods = request.getContactMethod().stream()
                    .map(contactMethodDTO -> {
                        ContactMethod contactMethod = ContactMethod.builder()
                                .contactType(contactMethodDTO.getContactType())
                                .contactUrl(contactMethodDTO.getContactUrl())
                                .build();
                        contactMethod.setPromotionProject(newPromotionProject);
                        return contactMethod;
                    })
                    .collect(Collectors.toSet());

            newPromotionProject.setContactMethodList(contactMethods);
        }

        return promotionProjectRepository.save(newPromotionProject);
    }

    // 프로젝트 홍보 수정
    @Override
    @Transactional
    public PromotionProject updatePromotionProject(PromotionProjectRequestDTO.UpdatePromotionProjectDTO request, Long promotionProjectId) {

        PromotionProject existingPromotionProject = promotionProjectRepository.findById(promotionProjectId)
                .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_NOT_FOUND));

        existingPromotionProject.setTitle(request.getTitle());
        existingPromotionProject.setIntro(request.getInfo());
        existingPromotionProject.setDescription(request.getDescription());

        promotionProjectMemberRepository.deleteByPromotionProject(existingPromotionProject);

        Set<Member> memberList = request.getPromotionProjectMember().stream()
                .map(memberId -> memberRepository.findById(memberId)
                        .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_ID_NOT_FOUND)))
                .collect(Collectors.toSet());

        Set<PromotionProjectMember> newMembers = PromotionProjectMemberConverter.toPromotionProjectMemberList(memberList);
        newMembers.forEach(promotionProjectMember -> {promotionProjectMember.setPromotionProject(existingPromotionProject);});

        existingPromotionProject.setPromotionProjectMemberList(newMembers);

        // 컨텍트 방식
        if (request.getContactMethod() != null) {
            existingPromotionProject.getContactMethodList().clear();

            request.getContactMethod().forEach(contactMethodDTO -> {
                ContactMethod contactMethod = ContactMethod.builder()
                        .contactType(contactMethodDTO.getContactType())
                        .contactUrl(contactMethodDTO.getContactUrl())
                        .promotionProject(existingPromotionProject)
                        .build();
                existingPromotionProject.getContactMethodList().add(contactMethod);
            });

        }
        return promotionProjectRepository.save(existingPromotionProject);
    }

    // 프로젝트 홍보 삭제
    public Void deletePromotionProject(Long promotionProjectId) {
        PromotionProject existingPromotionProject = promotionProjectRepository.findById(promotionProjectId)
                .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_NOT_FOUND));

        promotionProjectRepository.deleteById(existingPromotionProject.getId());
        return null;
    }

    // 프로젝트 홍보글 모아보기 (인기순/최신순)
    @Override
    @Transactional(readOnly = true)
    public Page<PromotionProject> getPromotionProjectList(Integer page, Integer sort){
        QPromotionProject promotionProject = QPromotionProject.promotionProject;

        Pageable pageable = PageRequest.of(page, 12);

        JPQLQuery<PromotionProject> query = queryFactory.selectFrom(promotionProject);

        if (sort != null && sort == 0) {
            List<Long> top3Ids = queryFactory
                    .select(promotionProject.id)
                    .from(promotionProject)
                    .orderBy(promotionProject.views.desc())
                    .limit(3)
                    .fetch();

            query.where(promotionProject.id.notIn(top3Ids))
                    .orderBy(promotionProject.views.desc());
        } else {
            query.orderBy(promotionProject.createdAt.desc());
        }

        query.offset(pageable.getOffset()).limit(pageable.getPageSize());

        List<PromotionProject> content = query.fetch();

        long total = queryFactory
                .selectFrom(promotionProject)
                .where(sort != null && sort == 0 ? promotionProject.id.notIn(
                        queryFactory
                                .select(promotionProject.id)
                                .from(promotionProject)
                                .orderBy(promotionProject.views.desc())
                                .limit(3)
                                .fetch()
                ) : null)
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    // 프로젝트 홍보글 모아보기 (인기 top3)
    @Override
    @Transactional(readOnly = true)
    public List<PromotionProject> getPromotionProjectTop3(){
        QPromotionProject promotionProject = QPromotionProject.promotionProject;

        return queryFactory.selectFrom(promotionProject)
                .orderBy(promotionProject.views.desc())
                .limit(3)
                .fetch();
    }

    // 프로젝트 홍보 모아보기 (검색)
    @Override
    @Transactional(readOnly = true)
    public Page<PromotionProject> getPromotionProjectSearchList(Integer page, String keyword){
        QPromotionProject promotionProject = QPromotionProject.promotionProject;

        Pageable pageable = PageRequest.of(page, 12);

        JPQLQuery<PromotionProject> query = queryFactory.selectFrom(promotionProject);
        query.where(promotionProject.title.containsIgnoreCase(keyword))
                .orderBy(promotionProject.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<PromotionProject> content = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);
    }


    // 프로젝트 모집글 상세페이지 조회
    @Override
    public PromotionProject getPromotionProject(Long promotionProjectId){
        PromotionProject promotionProject = promotionProjectRepository.findPromotionProjectDetails(promotionProjectId);

        if (promotionProject == null){
            throw new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_ID_NOT_FOUND);
        }
        return promotionProject;
    }
}
