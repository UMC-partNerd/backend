package com.partnerd.service.collabAskService;

import com.partnerd.domain.mapping.CollabAsk;
import com.partnerd.web.dto.collabDTO.request.CollabAskRequestDTO;

public interface CollabAskCommandService {

    CollabAsk addCollabAsk(Long collabPostId, Long memberId);
    void deleteCollabAsk(Long collabAskId, Long memberId);

}
