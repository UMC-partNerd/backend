package com.partnerd.repository.projectRepository.projectLikes;

import com.partnerd.domain.mapping.ProjectLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectLikesRepository extends JpaRepository<ProjectLikes, Long>, ProjectLikesRepositoryCustom {

}
