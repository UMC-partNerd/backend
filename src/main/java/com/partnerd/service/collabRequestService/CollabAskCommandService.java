package com.partnerd.service.collabRequestService;

import com.partnerd.domain.mapping.CollabAsk;
import com.partnerd.web.dto.collabDTO.request.CollabAskRequestDTO;

public interface CollabAskCommandService {

    CollabAsk addCollabAsk(CollabAskRequestDTO.addCollabAskRquestDTO requestDTO);

}
