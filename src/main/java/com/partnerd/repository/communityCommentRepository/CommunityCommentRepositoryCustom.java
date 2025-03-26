package com.partnerd.repository.communityCommentRepository;

import com.partnerd.mongoRepository.domain.CommunityComment;

import java.util.List;

public interface CommunityCommentRepositoryCustom {

    // 커뮤니티 댓글 전체 조회
    List<CommunityComment> findCommunityCommentList(Long communityId);
}
