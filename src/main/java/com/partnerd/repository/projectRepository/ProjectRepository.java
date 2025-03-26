package com.partnerd.repository.projectRepository;


import com.partnerd.mongoRepository.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustiom {

    List<Project> findTop6ByOrderByCreatedAtDesc();
}
