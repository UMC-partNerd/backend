package com.partnerd.repository.communityCommentRepository;

import com.partnerd.mongoRepository.domain.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long>, CommunityCommentRepositoryCustom {
}
