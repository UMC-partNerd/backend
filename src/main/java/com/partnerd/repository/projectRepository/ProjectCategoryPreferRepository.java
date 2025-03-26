package com.partnerd.repository.projectRepository;

import com.partnerd.mongoRepository.domain.Project;
import com.partnerd.mongoRepository.domain.mapping.ProjectCategoryPrefer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectCategoryPreferRepository extends JpaRepository<ProjectCategoryPrefer, Long> {
    void deleteByProject(Project project);
}