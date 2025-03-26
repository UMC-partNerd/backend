package com.partnerd.service.collabAskService;

import com.partnerd.domain.mapping.CollabAsk;

public interface CollabAskCommandService {

    CollabAsk addCollabAsk(Long collabPostId, Long memberId);
    void deleteCollabAsk(Long collabAskId, Long memberId);

}
