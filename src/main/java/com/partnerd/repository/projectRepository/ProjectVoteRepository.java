package com.partnerd.repository.projectRepository;

import com.partnerd.mongoRepository.domain.mapping.ProjectVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectVoteRepository extends JpaRepository<ProjectVote, Long>, ProjectVoteRepositoryCustom {

}
