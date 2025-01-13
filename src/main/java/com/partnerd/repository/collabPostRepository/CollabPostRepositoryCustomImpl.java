package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.CollabPost;
import com.partnerd.domain.QCategory;
import com.partnerd.domain.QCollabPost;
import com.partnerd.domain.mapping.QCollabPostCategory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollabPostRepositoryCustomImpl implements CollabPostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QCollabPost qCollabPost = QCollabPost.collabPost;
    private final QCollabPostCategory qCollabPostCategory = QCollabPostCategory.collabPostCategory;

    private final QCategory qCategory = QCategory.category;

    @Override
    public Page<CollabPost> findAllWithCategories(Pageable pageable) {
        JPAQuery<CollabPost> query = queryFactory
                .selectFrom(qCollabPost)
                .leftJoin(qCollabPost.collabPostCategoryList, qCollabPostCategory)
                .fetchJoin()
                .leftJoin(qCollabPostCategory.category, qCategory) // Category도 즉시 로딩
                .fetchJoin();

        Sort sort = pageable.getSort(); // pageable에서 정렬 정보 추출
        for (Sort.Order order : sort) {  // Sort에 포함된 각 Order를 반복
            String property = order.getProperty();  // 정렬 기준 필드 이름
            if ("createdAt".equalsIgnoreCase(property)) {
                    query.orderBy(qCollabPost.createdAt.desc());
            } else {
                query.orderBy(qCollabPost.end_date.asc());
            }
        }

        long total = query.fetch().size();
        List<CollabPost> results = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        return new PageImpl<>(results, pageable, total);
    }
}
