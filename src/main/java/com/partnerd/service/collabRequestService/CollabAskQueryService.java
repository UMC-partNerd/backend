package com.partnerd.service.collabRequestService;

import com.partnerd.domain.mapping.CollabAsk;
import org.springframework.data.domain.Page;

public interface CollabAskQueryService {

    Page<CollabAsk> getCollabAskList(Integer page, Integer askType, Long memberId);

}
