package com.partnerd.repository.projectRepository;

import com.partnerd.domain.Project;
import com.partnerd.domain.mapping.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    void deleteByProject(Project project);
}