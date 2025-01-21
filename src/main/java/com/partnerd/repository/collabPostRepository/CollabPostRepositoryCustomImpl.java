package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.*;
import com.partnerd.domain.CollabPost;
import com.partnerd.domain.QCategory;
import com.partnerd.domain.QCollabPost;
import com.partnerd.domain.QMember;
import com.partnerd.domain.mapping.QClubMember;
import com.partnerd.domain.mapping.QCollabPostCategory;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CollabPostRepositoryCustomImpl implements CollabPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QCollabPost qCollabPost = QCollabPost.collabPost;
    private final QCollabPostCategory qCollabPostCategory = QCollabPostCategory.collabPostCategory;

    private final QCategory qCategory = QCategory.category;
    private final QCollabInquiry qCollabInquiry = QCollabInquiry.collabInquiry;
    private final QEventType qEventType = QEventType.eventType;
    private final QMember qMember = QMember.member;
    private final QClubMember qClubMember = QClubMember.clubMember;
    private final QContactMethod qContactMethod = QContactMethod.contactMethod;


    private void applySorting(JPAQuery<CollabPost> query, Pageable pageable) {
        Sort sort = pageable.getSort();
        for (Sort.Order order : sort) {
            String property = order.getProperty();
            if ("createdAt".equalsIgnoreCase(property)) {
                query.orderBy(qCollabPost.createdAt.desc());
            } else {
                query.orderBy(qCollabPost.endDate.asc());
            }
        }
    }

    @Override
    public Page<CollabPost> findAllWithCategories(Pageable pageable) {
        JPAQuery<CollabPost> query = queryFactory
                .selectFrom(qCollabPost)
                .leftJoin(qCollabPost.collabPostCategoryList, qCollabPostCategory)
                .fetchJoin()
                .leftJoin(qCollabPostCategory.category, qCategory) // Category도 즉시 로딩
                .fetchJoin();

       applySorting(query, pageable);

        long total = query.fetch().size();
        List<CollabPost> results = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        return new PageImpl<>(results, pageable, total);
    }


    @Override
    public Page<CollabPost> findAllByCategories(Pageable pageable, List<Long> categories) {

        JPAQuery<CollabPost> query = queryFactory
                .selectFrom(qCollabPost)
                .leftJoin(qCollabPost.collabPostCategoryList, qCollabPostCategory).fetchJoin()
                .leftJoin(qCollabPostCategory.category, qCategory).fetchJoin()
                .where(qCollabPost.collabPostCategoryList.any().category.id.in(categories))
                .distinct();

        applySorting(query, pageable);

        QueryResults<CollabPost> queryResults = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        return new PageImpl<>(queryResults.getResults(), pageable,  queryResults.getTotal());
    }


    @Override
    public CollabPost findCollabPostDetails(Long collabPostId) {

        JPAQuery<CollabPost> query = queryFactory
                .selectFrom(qCollabPost).distinct()
                .leftJoin(qCollabPost.clubMember, qClubMember).fetchJoin()
                .leftJoin(qClubMember.member, qMember).fetchJoin()
                .leftJoin(qCollabPost.collabInquiryList, qCollabInquiry).fetchJoin()
                .leftJoin(qCollabPost.contactMethodList, qContactMethod).fetchJoin()
                .leftJoin(qCollabPost.collabPostCategoryList, qCollabPostCategory).fetchJoin()
                .leftJoin(qCollabPostCategory.category, qCategory).fetchJoin()
                .leftJoin(qCollabPost.eventType, qEventType).fetchJoin()
                .where(qCollabPost.id.eq(collabPostId));

        return query.fetchOne();
      
    }

    @Override
    public Optional<CollabPost> findByIdWithMember(Long collabPostId) {

        JPAQuery<CollabPost> query = queryFactory
                .selectFrom(qCollabPost)
                .leftJoin(qCollabPost.clubMember, qClubMember).fetchJoin()
                .leftJoin(qClubMember.member, qMember).fetchJoin()
                .where(qCollabPost.id.eq(collabPostId));

        Optional<CollabPost> collabPost = Optional.ofNullable(query.fetchOne());

        return collabPost;

    }

}
