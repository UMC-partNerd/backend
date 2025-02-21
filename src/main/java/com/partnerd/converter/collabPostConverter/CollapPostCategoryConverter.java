package com.partnerd.converter.collabPostConverter;

import com.partnerd.domain.Category;
import com.partnerd.domain.mapping.CollabPostCategory;

import java.util.List;
import java.util.stream.Collectors;

public class CollapPostCategoryConverter {

    public static List<CollabPostCategory> toCollabPostCategoryList(List<Category> categorieList) {
            List<CollabPostCategory> collabPostCategoryList = categorieList.stream()
                    .map(category -> {
                        CollabPostCategory collabPostCategory = CollabPostCategory.builder()
                                .category(category)
                                .build();
                        return collabPostCategory;
                    }).collect(Collectors.toList());
        return collabPostCategoryList;
    }

}
