package com.partnerd.service.collabPostService;

import com.partnerd.mongoRepository.domain.CollabPost;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;

import java.util.List;

public interface CollabPostQueryService {

    CollabPostResponseDTO.PagingResultDTO<CollabPostResponseDTO.CollabPostPreviewDTO> getCollabPostList(CollabPostRequestDTO.RequestNoOffsetPagingDTO requestNoOffsetPagingDTO);

    CollabPostResponseDTO.PagingResultDTO<CollabPostResponseDTO.CollabPostPreviewDTO> getCollabPostListByCategory(CollabPostRequestDTO.RequestNoOffsetPagingDTO requestNoOffsetPagingDTO, List<Long> categories);

    CollabPost getCollabPost(Long collabPostId);

    // 마이페이지 - 내가 쓴 콜라보레이션 모아보기
    List<CollabPost> getMyCollabPosts(Long memberId);
}
