package com.partnerd.service.collabPostService.impl;

import com.partnerd.domain.CollabPost;
import com.partnerd.repository.collabPostRepository.CollabPostRepository;
import com.partnerd.service.collabPostService.CollabPostQueryService;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollabPostQueryServiceImpl implements CollabPostQueryService {

    private final CollabPostRepository collabPostRepository;

    @Override
    public Page<CollabPost> getCollabPostList(Integer page, String sortBy){

        Pageable pageable = PageRequest.of(page, 9, Sort.by(Sort.Order.desc(sortBy)));

        return collabPostRepository.findAllWithCategories(pageable);
    }

    @Override
    public Page<CollabPost> getCollabPostListByCategory(List<String> categories, Integer page, String sortBy) {

        Pageable pageable = PageRequest.of(page, 9, Sort.by(Sort.Order.desc(sortBy)));


        return collabPostRepository.findAllByCategories(pageable, categories);
    }
}
