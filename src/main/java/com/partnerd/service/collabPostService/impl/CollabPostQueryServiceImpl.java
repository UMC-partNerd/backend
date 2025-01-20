package com.partnerd.service.collabPostService.impl;

import com.partnerd.domain.CollabPost;
import com.partnerd.repository.collabPostRepository.CollabPostRepository;
import com.partnerd.service.collabPostService.CollabPostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollabPostQueryServiceImpl implements CollabPostQueryService {

    private final CollabPostRepository collabPostRepository;


    @Override
    @Transactional(readOnly=true)
    public Page<CollabPost> getCollabPostList(Integer page, String sortBy){

        Pageable pageable = PageRequest.of(page, 9, Sort.by(Sort.Order.desc(sortBy)));

        return collabPostRepository.findAllWithCategories(pageable);
    }

    @Override
    @Transactional(readOnly=true)
    public CollabPost getCollabPost(Long collabPostId) {

        return collabPostRepository.findCollabPostDetails(collabPostId);
    }


}
