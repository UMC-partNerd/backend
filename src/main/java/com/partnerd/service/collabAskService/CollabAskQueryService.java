package com.partnerd.service.collabAskService;

import com.partnerd.mongoRepository.domain.mapping.CollabAsk;
import org.springframework.data.domain.Page;

public interface CollabAskQueryService {

    Page<CollabAsk> getCollabAskList(Integer page, Integer askType, Long memberId);

}
