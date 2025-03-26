package com.partnerd.service.categoryService;

import com.partnerd.mongoRepository.domain.Category;

public interface CategoryService {

    Category findCategoryById(Long id);
    Category addCategory(String name);


}
