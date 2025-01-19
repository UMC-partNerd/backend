package com.partnerd.service.collabPostService;

import com.partnerd.domain.CollabPost;
import org.springframework.data.domain.Page;

public interface CollabPostQueryService {

    Page<CollabPost> getCollabPostList(Integer page, String sortBy);
    CollabPost getCollabPost(Long collabPostId);




}
