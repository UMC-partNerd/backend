package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.*;
import com.partnerd.domain.mapping.QClubMember;
import com.partnerd.domain.mapping.QCollabPostCategory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Builder;
import lombok.Getter;
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
    private final QCollabPostImg qCollabPostImg = QCollabPostImg.collabPostImg;


    private PagingResultDTO applySortingAndPaging(JPAQuery<CollabPost> query, Pageable pageable) {
        Sort sort = pageable.getSort();
        for (Sort.Order order : sort) {
            String property = order.getProperty();
            if ("createdAt".equalsIgnoreCase(property)) {
                query.orderBy(qCollabPost.createdAt.desc());
            } else {
                query.orderBy(qCollabPost.endDate.asc());
            }
        }
        long total = queryFactory
                .select(qCollabPost.count())
                .from(qCollabPost)
                .fetchOne();

        List<CollabPost> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PagingResultDTO.builder()
                .total(total)
                .results(results)
                .build();

    }

    @Override
    public Page<CollabPost> findAllWithCategories(Pageable pageable) {
        JPAQuery<CollabPost> query = queryFactory
                .selectFrom(qCollabPost)
                .leftJoin(qCollabPost.collabPostCategoryList, qCollabPostCategory)
                .fetchJoin()
                .leftJoin(qCollabPostCategory.category, qCategory) // Category도 즉시 로딩
                .fetchJoin();

       PagingResultDTO pagingResultDTO = applySortingAndPaging(query, pageable);

        return new PageImpl<>(pagingResultDTO.getResults(), pageable, pagingResultDTO.getTotal());
    }


    @Override
    public Page<CollabPost> findAllByCategories(Pageable pageable, List<Long> categories) {

        JPAQuery<CollabPost> query = queryFactory
                .selectFrom(qCollabPost)
                .leftJoin(qCollabPost.collabPostCategoryList, qCollabPostCategory).fetchJoin()
                .leftJoin(qCollabPostCategory.category, qCategory).fetchJoin()
                .where(qCollabPost.collabPostCategoryList.any().category.id.in(categories))
                .distinct();

        PagingResultDTO pagingResultDTO = applySortingAndPaging(query, pageable);

        return new PageImpl<>(pagingResultDTO.getResults(), pageable, pagingResultDTO.getTotal());
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
                .leftJoin(qCollabPost.collabPostImgList, qCollabPostImg).fetchJoin()
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



    // 마이페이지 - 내가 쓴 콜라보레이션 모아보기
    @Override
    public List<CollabPost> findCollabPostsByMemberId(Long memberId){
        return queryFactory
                .selectFrom(qCollabPost)
                .leftJoin(qCollabPost.clubMember, qClubMember).fetchJoin()
                .where(qClubMember.member.id.eq(memberId))
                .distinct()
                .fetch();
    }


    @Builder
    @Getter
    public static class PagingResultDTO {
        private long total;
        private List<CollabPost> results;
    }

}
