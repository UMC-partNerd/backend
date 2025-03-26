package com.partnerd.repository.projectRepository;

import com.partnerd.mongoRepository.domain.mapping.ProjectLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectLikesRepository extends JpaRepository<ProjectLikes, Long>, ProjectLikesRepositoryCustom {

}
