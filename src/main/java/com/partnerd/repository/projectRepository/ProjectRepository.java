package com.partnerd.repository.projectRepository;


import com.partnerd.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustiom {
}
