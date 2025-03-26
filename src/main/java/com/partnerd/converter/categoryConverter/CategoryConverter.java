package com.partnerd.converter.categoryConverter;

import com.partnerd.mongoRepository.domain.Category;
import com.partnerd.web.dto.categoryDTO.CategoryDTO;

public class CategoryConverter {

    public static CategoryDTO toCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();

    }
}
