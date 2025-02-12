package com.partnerd.repository.projectRepository;


import com.partnerd.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

    List<Project> findTop6ByOrderByCreatedAtDesc();
}
