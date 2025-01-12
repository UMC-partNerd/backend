package com.partnerd.service.collabPostService;

import com.partnerd.domain.CollabPost;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;

public interface CollabPostCommandService {

    CollabPost addCollabPost(CollabPostRequestDTO.RequestCollabPostDTO requestDTO);

    CollabPost modifyCollabPost(Long collabPostId, CollabPostRequestDTO.RequestCollabPostDTO requestDTO);

    void deleteCollabPost(Long collabPostId);

}
