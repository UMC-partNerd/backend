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

@Service
@RequiredArgsConstructor
public class CollabPostQueryServiceImpl implements CollabPostQueryService {

    private final CollabPostRepository collabPostRepository;

    @Override
    public Page<CollabPost> getCollabPostList(Integer page, String sortBy){

        Sort sort;
        if ("endDate".equalsIgnoreCase(sortBy)) {
            // 마감일을 기준으로 오름차순 정렬
            sort = Sort.by(Sort.Order.asc("endDate"));
        } else {
            // 기본적으로 생성일을 기준으로 내림차순 정렬
            sort = Sort.by(Sort.Order.desc("createdAt"));
        }

        Pageable pageable = PageRequest.of(page, 9, sort);

        return collabPostRepository.findAllWithCategories(pageable);
    }
}
