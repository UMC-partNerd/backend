package com.partnerd.service.collabPostService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CollabPostHandler;
import com.partnerd.mongoRepository.domain.CollabPost;
import com.partnerd.repository.collabPostRepository.collabPost.CollabPostRepository;
import com.partnerd.service.collabPostService.CollabPostQueryService;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollabPostQueryServiceImpl implements CollabPostQueryService {

    private final CollabPostRepository collabPostRepository;


    // No offset 페이징 콜라보 글 전체 조회
    @Override
    @Transactional(readOnly=true)
    public CollabPostResponseDTO.PagingResultDTO<CollabPostResponseDTO.CollabPostPreviewDTO> getCollabPostList(CollabPostRequestDTO.RequestNoOffsetPagingDTO requestNoOffsetPagingDTO){

        // 페이징 시
        // Pageable pageable = PageRequest.of(page, 9, Sort.by(Sort.Order.desc(sortBy)));

        //  No offset 페이징
        return collabPostRepository.findAllWithNoOffset(requestNoOffsetPagingDTO);
    }

    // 카테고리 별 콜라보 글 조회
    @Override
    @Transactional(readOnly=true)
    public  CollabPostResponseDTO.PagingResultDTO<CollabPostResponseDTO.CollabPostPreviewDTO> getCollabPostListByCategory(CollabPostRequestDTO.RequestNoOffsetPagingDTO requestNoOffsetPagingDTO,
                                                                                                                          List<Long> categories) {
        // 페이징 시
       // Pageable pageable = PageRequest.of(page, 9, Sort.by(Sort.Order.desc(sortBy)));

        //  No offset 페이징
        return collabPostRepository.findAllByCategoriesWithNoOffset(requestNoOffsetPagingDTO, categories);
    }

    @Override
    @Transactional(readOnly=true)
    public CollabPost getCollabPost(Long collabPostId) {

        CollabPost collabPost = collabPostRepository.findCollabPostDetails(collabPostId);
        if (collabPost == null) {
            throw new CollabPostHandler(ErrorStatus.COLLAB_POST_NOT_FOUND);
        }

        return collabPost;
    }


    // 마이페이지 - 내가 쓴 콜라보레이션 모아보기
    @Override
    @Transactional(readOnly=true)
    public List<CollabPost> getMyCollabPosts(Long memberId) {
        return collabPostRepository.findCollabPostsByMemberId(memberId);
    }
}
