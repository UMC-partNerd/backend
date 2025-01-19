package com.partnerd.repository.projectRepository;

import com.partnerd.domain.Project;
import com.partnerd.domain.mapping.ProjectCategoryPrefer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectCategoryPreferRepository extends JpaRepository<ProjectCategoryPrefer, Long> {
    void deleteByProject(Project project);
}