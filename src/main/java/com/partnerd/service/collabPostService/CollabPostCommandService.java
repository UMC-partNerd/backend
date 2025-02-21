package com.partnerd.service.collabPostService;

import com.partnerd.domain.CollabPost;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;

public interface CollabPostCommandService {


    CollabPost addCollabPost(CollabPostRequestDTO.RequestCollabPostDTO requestDTO, Long memberId);

    CollabPost modifyCollabPost(Long collabPostId, CollabPostRequestDTO.RequestCollabPostDTO requestDTO, Long memberId);

    void deleteCollabPost(Long collabPostId, Long memberId);

    void deleteCollabPostAllById(Long id);

}
