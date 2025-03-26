package com.partnerd.repository.projectRepository;

import com.partnerd.mongoRepository.domain.Member;
import com.partnerd.mongoRepository.domain.ProjectComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Long>, ProjectCommentRepositoryCustom {
    Optional<ProjectComment> findByIdAndMember(Long commentId, Member member);
}
