package com.partnerd.repository.communityCommentRepository;

import com.partnerd.domain.mapping.CommunityCommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCommentLikesRepository extends JpaRepository<CommunityCommentLikes, Long>, CommunityCommentLikesRepositoryCustom {

}
