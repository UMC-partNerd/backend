package com.partnerd.repository.collabPostRepository.collabPost;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.partnerd.domain.*;
import com.partnerd.domain.enums.ImageType;
import com.partnerd.domain.mapping.QClubMember;
import com.partnerd.domain.mapping.QCollabPostCategory;
import com.partnerd.web.dto.categoryDTO.CategoryDTO;
import com.partnerd.web.dto.categoryDTO.CollabPostCategoryDTO;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import com.partnerd.web.dto.homeDTO.response.HomeCollabPostDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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


    private PagingResultDTO applySortingAndPaging(JPAQuery<CollabPostResponseDTO.CollabPostPreviewDTO> query, Pageable pageable) {
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

        List<CollabPostResponseDTO.CollabPostPreviewDTO> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PagingResultDTO.builder()
                .total(total)
                .results(results)
                .build();

    }

    @Override
    public Page<CollabPostResponseDTO.CollabPostPreviewDTO> findAllWithCategories(Pageable pageable) {

        // 서브쿼리 활용하여 `mainImgKeyname` 가져오기
        QCollabPostImg subQCollabPostImg = new QCollabPostImg("subQCollabPostImg");
        SubQueryExpression<String> mainImgSubQuery = JPAExpressions
                .select(subQCollabPostImg.keyName)
                .from(subQCollabPostImg)
                .where(subQCollabPostImg.collabPost.id.eq(qCollabPost.id)
                        .and(subQCollabPostImg.imageType.eq(ImageType.MAIN)))
                .limit(1);

        JPAQuery<CollabPostResponseDTO.CollabPostPreviewDTO> query = queryFactory
                .select(Projections.constructor(
                        CollabPostResponseDTO.CollabPostPreviewDTO.class,
                        qCollabPost.id,
                        qCollabPost.title,
                        qCollabPost.startDate,
                        qCollabPost.endDate,
                        mainImgSubQuery
                )).from(qCollabPost);

        PagingResultDTO pagingResultDTO = applySortingAndPaging(query, pageable);
        List<CollabPostResponseDTO.CollabPostPreviewDTO> collabPosts = pagingResultDTO.getResults();

        // 콜라보 글 별로 해당하는 카테고리 DTO 찾기.
        getCategoryListDTO(collabPosts);

        return new PageImpl<>(pagingResultDTO.getResults(), pageable, pagingResultDTO.getTotal());
    }

    @Override
    public CollabPostResponseDTO.PagingResultDTO<CollabPostResponseDTO.CollabPostPreviewDTO> findAllWithNoOffset(CollabPostRequestDTO.RequestNoOffsetPagingDTO requestNoOffsetPagingDTO) {

        int pageNum = requestNoOffsetPagingDTO.getPageNum();
        Date lastEndDate = requestNoOffsetPagingDTO.getLastEndDate();
        LocalDateTime lastCreatedAt = requestNoOffsetPagingDTO.getLastCreatedAt();
        Long lastId = requestNoOffsetPagingDTO.getLastId();
        int size = requestNoOffsetPagingDTO.getSize();
        String sortBy = requestNoOffsetPagingDTO.getSortBy();

        OrderSpecifier<?>[] orderSpecifiers = getOrderSpecifier(sortBy);
        BooleanExpression paginationCondition = getPageCondition(sortBy, lastEndDate, lastCreatedAt, lastId);

        List<CollabPostResponseDTO.CollabPostPreviewDTO> collabPosts = queryFactory
                .select(Projections.constructor(
                        CollabPostResponseDTO.CollabPostPreviewDTO.class,
                        qCollabPost.id,
                        qCollabPost.title,
                        qCollabPost.startDate,
                        qCollabPost.endDate,
                        JPAExpressions.select(qCollabPostImg.keyName)
                                .from(qCollabPostImg)
                                .where(qCollabPostImg.collabPost.id.eq(qCollabPost.id)
                                        .and(qCollabPostImg.imageType.eq(ImageType.MAIN)))
                                .limit(1)
                ))
                .from(qCollabPost)
                .where(paginationCondition) // Keyset Pagination 적용
                .orderBy(orderSpecifiers)
                .limit(size + 1)  // 마지막 페이지 판별을 위해 `size+1`개 조회
                .fetch();
        // 콜라보 글 별로 해당하는 카테고리 DTO 찾기.
        getCategoryListDTO(collabPosts);
        // 마지막 페이지인지 확인
        boolean isLast = collabPosts.size() <= size;
        if (!isLast) {
            collabPosts.remove(collabPosts.size() - 1); // ✅ 다음 페이지 데이터 제거
        }

        InitialFetchDTO initialFetchDTO = pageNum % 10 == 1 ? initialFetchPage(size, sortBy, lastEndDate, lastCreatedAt, lastId) : null;

        return CollabPostResponseDTO.PagingResultDTO.<CollabPostResponseDTO.CollabPostPreviewDTO>builder()
                .data(collabPosts)
                .listSize(collabPosts.size())
                .hasMorePages(initialFetchDTO != null ? initialFetchDTO.hasMorePages : false)  // 첫페이지 요청시 10페이지 이상의 데이터가 더 있는지 여부
                .availablePages(initialFetchDTO != null ? initialFetchDTO.availablePages : -1) // 첫페이지 요청시 몇 페이지를 표시할 수 있는지 확인할 수 있는 최대 페이지 번호
                .pageReferenceDTOList(initialFetchDTO != null ? initialFetchDTO.getPageReferenceDTOList() : null)
                .isFirst(pageNum == 1)
                .isLast(isLast)
                .build();
    }


    private void getCategoryListDTO (List<CollabPostResponseDTO.CollabPostPreviewDTO> collabPosts) {
        // 각 collabPost의 ID 리스트 추출
        List<Long> postIds = collabPosts.stream().map(CollabPostResponseDTO.CollabPostPreviewDTO::getCollabPostId).collect(Collectors.toList());

        Map<Long, List<CollabPostCategoryDTO>> categoryMap = queryFactory.
                select(Projections.constructor(
                        CollabPostCategoryDTO.class,
                        qCollabPostCategory.collabPost.id,
                        qCategory.id,
                        qCategory.name
                ))
                .from(qCollabPostCategory)
                .join(qCategory).on(qCollabPostCategory.category.id.eq(qCategory.id))
                .where(qCollabPostCategory.collabPost.id.in(postIds))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(CollabPostCategoryDTO::getCollabPostId));

        collabPosts.forEach(post -> {
            post.setCategoryDTOList(categoryMap.getOrDefault(post.getCollabPostId(), Collections.emptyList()));
        });

    }
    // 정렬기준에 따른 동적 정렬
    private OrderSpecifier<?>[] getOrderSpecifier(String sortBy) {

        if ("endDate".equalsIgnoreCase(sortBy)) {
            return new OrderSpecifier<?>[]{
                    new OrderSpecifier<>(Order.ASC, qCollabPost.endDate),
                    new OrderSpecifier<>(Order.ASC, qCollabPost.id)
            };
        } else {
            return new OrderSpecifier<?>[]{
                    new OrderSpecifier<>(Order.DESC, qCollabPost.createdAt),
                    new OrderSpecifier<>(Order.DESC, qCollabPost.id)
            };
        }
    }

    // 마지막 글에 대한 key Pagination
    private  BooleanExpression getPageCondition (String sortBy, Date lastEndDate, LocalDateTime lastCreatedAt, Long lastId) {

        if ("endDate".equalsIgnoreCase(sortBy) && lastEndDate != null && lastId != null) {
            return qCollabPost.id.gt(lastId)  // 먼저 `id` 기준으로 중복 데이터 필터링
                    .and(qCollabPost.endDate.goe(lastEndDate)); // 그다음 `endDate` 조건 적용

        } else if (lastCreatedAt != null && lastId != null) {
            return qCollabPost.id.gt(lastId)  // 최신순일 경우도 `id` 먼저 필터링
                    .and(qCollabPost.createdAt.loe(lastCreatedAt));
        }
        return Expressions.TRUE;
    }

    // 최대 10 페이지까지의 개수 조회 메서드
    private InitialFetchDTO initialFetchPage(int size, String sortBy, Date lastEndDate, LocalDateTime lastCreatedAt, Long lastId) {

        int maxInitialPages = 10; // 처음에 표시할 최대 페이지 개수
        int initialFetchSize = size * maxInitialPages; // 10페이지까지 확인할 개수

        OrderSpecifier<?>[] orderSpecifiers = getOrderSpecifier(sortBy);
        BooleanExpression paginationCondition = getPageCondition(sortBy, lastEndDate, lastCreatedAt, lastId);

        // 10페이지까지 데이터가 있는지 확인
        List<Tuple> pageCheck = queryFactory
                .select(qCollabPost.id, qCollabPost.endDate, qCollabPost.createdAt) // 전체 개수를 구하지 않고 10페이지까지 ID만 조회
                .from(qCollabPost)
                .where(paginationCondition)
                .orderBy(orderSpecifiers) // 정렬 방식 적용
                .limit(initialFetchSize + 1) // 10페이지 + 1개 데이터만 확인
                .fetch();

        boolean hasMorePages = pageCheck.size() > initialFetchSize; // 10페이지 이상 존재 여부 확인
        List<PageReferenceDTO> pageReferences = new ArrayList<>();

        for (int i = 0; i < Math.min(pageCheck.size(), initialFetchSize); i += size) {
            Tuple lastTuple = pageCheck.get(i);

            // `sortBy` 값에 따라 적절한 필드 설정
            PageReferenceDTO pageReference;
            if ("endDate".equalsIgnoreCase(sortBy)) {
                pageReference = PageReferenceDTO.builder()
                        .lastId(lastTuple.get(qCollabPost.id))
                        .lastEndDate(lastTuple.get(qCollabPost.endDate)) // endDate 정렬 시 설정
                        .build();
            } else {
                pageReference = PageReferenceDTO.builder()
                        .lastId(lastTuple.get(qCollabPost.id))
                        .lastCreatedAt(lastTuple.get(qCollabPost.createdAt)) // createdAt 정렬 시 설정
                        .build();
            }

            pageReferences.add(pageReference);
        }
        // 현재 표시할 수 있는 페이지 수 (실제 데이터 개수 기준)
        int availablePages = (int) Math.ceil((double) Math.min(pageCheck.size(),initialFetchSize) / size);

        return InitialFetchDTO.builder()
                .hasMorePages(hasMorePages)
                .availablePages(availablePages)
                .pageReferenceDTOList(pageReferences)
                .build();
    }

    @Getter
    @Builder
    private static class InitialFetchDTO {
        private boolean hasMorePages;
        private int availablePages;
        private List<PageReferenceDTO> pageReferenceDTOList;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL) // null 값은 JSON 응답에서 제외
    public static class PageReferenceDTO {
        private Long lastId;
        private Date lastEndDate; // 마감일 기준 정렬 시 사용
        private LocalDateTime lastCreatedAt; // 최신순 정렬 시 사용

    }

   @Override
    public Page<CollabPost> findAllByCategories(Pageable pageable, List<Long> categories) {
    /*
        JPAQuery<CollabPost> query = queryFactory
                .selectFrom(qCollabPost)
                .leftJoin(qCollabPost.collabPostCategoryList, qCollabPostCategory).fetchJoin()
                .leftJoin(qCollabPostCategory.category, qCategory).fetchJoin()
                .where(qCollabPost.collabPostCategoryList.any().category.id.in(categories))
                .distinct();

        PagingResultDTO pagingResultDTO = applySortingAndPaging(query, pageable);

        return new PageImpl<>(pagingResultDTO.getResults(), pageable, pagingResultDTO.getTotal());
    */
       return null;
   }


    @Override
    public CollabPost findCollabPostDetails(Long collabPostId) {

        JPAQuery<CollabPost> query = queryFactory
                .selectFrom(qCollabPost).distinct()
                .leftJoin(qCollabPost.clubMember, qClubMember).fetchJoin()
                .leftJoin(qClubMember.member, qMember).fetchJoin()
                .leftJoin(qCollabPost.contactMethodList, qContactMethod).fetchJoin()
                .leftJoin(qCollabPost.collabPostCategoryList, qCollabPostCategory).fetchJoin()
                .leftJoin(qCollabPostCategory.category, qCategory).fetchJoin()
                .leftJoin(qCollabPost.eventType, qEventType).fetchJoin()
                .leftJoin(qCollabPost.collabPostImgList, qCollabPostImg).fetchJoin()
                .where(qCollabPost.id.eq(collabPostId));

        CollabPost collabPost = query.fetchOne();

        // CollabInquiryList와 연관된 Member 로드
        List<CollabInquiry> inquiries = queryFactory
                .selectFrom(qCollabInquiry)
                .leftJoin(qCollabInquiry.member, qMember).fetchJoin()
                .where(qCollabInquiry.collabPost.id.eq(collabPostId))
                .fetch();

        // CollabPost에 Inquiries를 설정
        collabPost.setCollabInquiryList(inquiries);

        return collabPost;
      
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

    // 홈화면 - 최신등록 콜라보 조회하기
    @Override
    public List<HomeCollabPostDTO> findTopCollabPosts(Pageable pageable) {
        QCollabPost collabPost = QCollabPost.collabPost;
        QClubMember clubMember = QClubMember.clubMember;
        QClub club = QClub.club;
        QClubImage clubImage = QClubImage.clubImage;

        return queryFactory
                .select(Projections.constructor(HomeCollabPostDTO.class,
                        collabPost.id,
                        collabPost.title,
                        collabPost.intro,
                        club.name,
                        clubImage.keyName))
                .from(collabPost)
                .join(collabPost.clubMember, clubMember)
                .join(clubMember.club, club)
                .leftJoin(club.clubImgList, clubImage)
                .on(clubImage.image_type.eq(ImageType.MAIN))
                .orderBy(collabPost.createdAt.desc())
                .limit(pageable.getPageSize())
                .fetch();
    }


    @Builder
    @Getter
    public static class PagingResultDTO {
        private long total;
        private List<CollabPostResponseDTO.CollabPostPreviewDTO> results;
    }

}
