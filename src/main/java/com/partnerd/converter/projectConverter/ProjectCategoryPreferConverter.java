package com.partnerd.converter.projectConverter;

import com.partnerd.domain.ProjectCategory;
import com.partnerd.domain.mapping.ProjectCategoryPrefer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectCategoryPreferConverter {

    // 프로젝트 모집글 생성
    public static Set<ProjectCategoryPrefer> toProjectCategoryPreferList (Set<ProjectCategory> projectCategoryList){

        return projectCategoryList.stream()
               .map(projectCategory ->
                       ProjectCategoryPrefer.builder()
                               .projectCategory(projectCategory)
                                .build()
                ).collect(Collectors.toSet());
    }
}
