package com.partnerd.service.collabPostService;

import com.partnerd.domain.CollabPost;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CollabPostQueryService {

    Page<CollabPost> getCollabPostList(Integer page, String sortBy);
    Page<CollabPost> getCollabPostListByCategory(List<Long> categories, Integer page, String sortBy);
    CollabPost getCollabPost(Long collabPostId);

    // 마이페이지 - 내가 쓴 콜라보레이션 모아보기
    List<CollabPost> getMyCollabPosts(Long memberId);
}
