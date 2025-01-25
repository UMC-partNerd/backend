package com.partnerd.service.collabAskService;

import com.partnerd.domain.mapping.CollabAsk;
import com.partnerd.web.dto.collabDTO.request.CollabAskRequestDTO;

public interface CollabAskCommandService {

    CollabAsk addCollabAsk(CollabAskRequestDTO.addCollabAskRquestDTO requestDTO);
    void deleteCollabAsk(Long collabAskId, Long memberId);

}
