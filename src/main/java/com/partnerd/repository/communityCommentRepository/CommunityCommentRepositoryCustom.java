package com.partnerd.repository.communityCommentRepository;

import com.partnerd.domain.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityCommentRepositoryCustom {

    // 커뮤니티 댓글 전체 조회
    List<CommunityComment> findCommunityCommentList(Long communityId);
}
