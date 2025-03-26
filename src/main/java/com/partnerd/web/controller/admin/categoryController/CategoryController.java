package com.partnerd.web.controller.admin.categoryController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.converter.categoryConverter.CategoryConverter;
import com.partnerd.mongoRepository.domain.Category;
import com.partnerd.service.categoryService.CategoryService;
import com.partnerd.web.dto.categoryDTO.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/")
    public ApiResponse<CategoryDTO> addCategory(@RequestParam(name = "name") String name) {
        Category category = categoryService.addCategory(name);

        return ApiResponse.onSuccess(CategoryConverter.toCategoryDTO(category));
    }

}
