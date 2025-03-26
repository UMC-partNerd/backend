package com.partnerd.repository.collabPostRepository.collabPost;

import com.partnerd.domain.CollabPost;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import com.partnerd.web.dto.homeDTO.response.HomeCollabPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CollabPostRepositoryCustom {
    Page<CollabPostResponseDTO.CollabPostPreviewDTO> findAllWithCategories(Pageable pageable);

    // No offset Paging
    CollabPostResponseDTO.PagingResultDTO<CollabPostResponseDTO.CollabPostPreviewDTO> findAllWithNoOffset(CollabPostRequestDTO.RequestNoOffsetPagingDTO requestNoOffsetPagingDTO);

    CollabPostResponseDTO.PagingResultDTO<CollabPostResponseDTO.CollabPostPreviewDTO> findAllByCategoriesWithNoOffset (CollabPostRequestDTO.RequestNoOffsetPagingDTO requestNoOffsetPagingDTO, List<Long> categories);
    Page<CollabPost> findAllByCategories(Pageable pageable, List<Long> categories);

    CollabPost findCollabPostDetails(Long collabPostId);

    Optional<CollabPost> findByIdWithMember(Long collabPostId);
    // 마이페이지 - 내가 쓴 콜라보레이션 모아보기
    List<CollabPost> findCollabPostsByMemberId(Long memberId);

    //홈화면 - 최신 콜라보레이션 4개반환
    List<HomeCollabPostDTO> findTopCollabPosts(Pageable pageable);
}
