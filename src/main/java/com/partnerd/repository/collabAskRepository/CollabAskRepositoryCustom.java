package com.partnerd.repository.collabAskRepository;

import com.partnerd.domain.mapping.CollabAsk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollabAskRepositoryCustom {

    Page<CollabAsk> findBySenderMemberId(Long senderId, Pageable pageable);
    Page<CollabAsk> findByReceiverMemberId(Long senderId, Pageable pageable);
}
