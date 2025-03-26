package com.partnerd.repository.categoryRepository;

import com.partnerd.domain.Category;
import com.partnerd.domain.QCategory;
import com.partnerd.domain.mapping.QCollabPostCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final QCategory qCategory = QCategory.category;
    private final QCollabPostCategory qCollabPostCategory = QCollabPostCategory.collabPostCategory;

    @Override
    public List<Category> findAllByIdWithCollabPostCategory(List<Long> categoryIds) {

        List<Category> categoryList = queryFactory
                .selectFrom(qCategory)
                .leftJoin(qCategory.collabPostCategoryList, qCollabPostCategory).fetchJoin()
                .where(qCategory.id.in(categoryIds))
                .fetch();

        return categoryList;
    }
}
