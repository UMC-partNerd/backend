package com.partnerd.service.categoryService;

import com.partnerd.domain.Category;

public interface CategoryService {

    Category findCategoryById(Long id);
    Category addCategory(String name);


}
