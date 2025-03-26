package com.partnerd.repository.projectRepository;

import com.partnerd.domain.ProjectCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, Long> {
}
