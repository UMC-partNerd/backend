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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Page<CollabPost> getCollabPostListByCategory(List<Long> categories, Integer page, String sortBy) {

        Pageable pageable = PageRequest.of(page, 9, Sort.by(Sort.Order.desc(sortBy)));

        return collabPostRepository.findAllByCategories(pageable, categories);
    }

    @Override
    @Transactional(readOnly=true)
    public CollabPost getCollabPost(Long collabPostId) {

        return collabPostRepository.findCollabPostDetails(collabPostId);
    }


    // 마이페이지 - 내가 쓴 콜라보레이션 모아보기
    @Override
    @Transactional(readOnly=true)
    public List<CollabPost> getMyCollabPosts(Long memberId) {
        return collabPostRepository.findCollabPostsByMemberId(memberId);
    }
}
