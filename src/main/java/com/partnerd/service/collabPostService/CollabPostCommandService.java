package com.partnerd.service.collabPostService;

import com.partnerd.domain.CollabPost;
import com.partnerd.web.dto.collabDTO.CollabPostRequestDTO;

public interface CollabPostCommandService {

    CollabPost addCollabPost(CollabPostRequestDTO.addCollabPostDTO requestDTO);

    CollabPost modifyCollabPost(CollabPostRequestDTO.modifyCollabPostDTO requestDTO);

    void deleteCollabPost(Long collabPostId);

}
