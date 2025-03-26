package com.partnerd.service.collabAskService.impl;

import com.partnerd.mongoRepository.domain.mapping.CollabAsk;
import com.partnerd.repository.collabAskRepository.CollabAskRepository;
import com.partnerd.service.collabAskService.CollabAskQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollabAskQueryServiceImpl implements CollabAskQueryService {

    private final CollabAskRepository collabAskRepository;
    @Override
    public Page<CollabAsk> getCollabAskList(Integer page, Integer askType, Long memberId) {
        Page<CollabAsk> collabAskList = null;

        Pageable pageable = PageRequest.of(page, 2);

        if (askType == 0) { // 보낸 요청
             collabAskList = collabAskRepository.findBySenderMemberId(memberId, pageable);

        } else { // 받은 요청
             collabAskList = collabAskRepository.findByReceiverMemberId(memberId, pageable);
        }

        return collabAskList;
    }
}

