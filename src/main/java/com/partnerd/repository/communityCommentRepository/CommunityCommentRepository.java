package com.partnerd.repository.communityCommentRepository;

import com.partnerd.domain.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long>, CommunityCommentRepositoryCustom {
}
