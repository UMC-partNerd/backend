package com.partnerd.repository.projectRepository;

import com.partnerd.domain.Member;
import com.partnerd.domain.ProjectComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Long>, ProjectCommentRepositoryCustom {
}
