package com.partnerd.service.collabPostService;

import com.partnerd.domain.CollabPost;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CollabPostQueryService {

    CollabPostResponseDTO.PagingResultDTO<CollabPostResponseDTO.CollabPostPreviewDTO> getCollabPostList(LocalDateTime lastCreatedAt, Date lastEndDate, String sortBy, int size);
/*
    Page<CollabPost> getCollabPostListByCategory(List<Long> categories, Integer page, String sortBy);
*/
    CollabPost getCollabPost(Long collabPostId);

    // 마이페이지 - 내가 쓴 콜라보레이션 모아보기
    List<CollabPost> getMyCollabPosts(Long memberId);
}
