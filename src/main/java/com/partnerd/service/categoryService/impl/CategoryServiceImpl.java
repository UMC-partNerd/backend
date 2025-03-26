package com.partnerd.service.categoryService.impl;

import com.partnerd.domain.Category;
import com.partnerd.repository.categoryRepository.CategoryRepository;
import com.partnerd.service.categoryService.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(Long id) {

        return categoryRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

    }

    @Override
    public Category addCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .build();
        return categoryRepository.save(category);
    }
}
