package com.partnerd.repository.projectRepository;

import com.partnerd.mongoRepository.domain.Project;
import com.partnerd.mongoRepository.domain.mapping.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    void deleteByProject(Project project);
}