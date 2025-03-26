package com.partnerd.repository.categoryRepository;

import com.partnerd.mongoRepository.domain.Category;

import java.util.List;

public interface CategoryRepositoryCustom {

    List<Category> findAllByIdWithCollabPostCategory(List<Long> categoryIds);

}
