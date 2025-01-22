package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.CollabPost;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface CollabPostRepositoryCustom {
    Page<CollabPost> findAllWithCategories(Pageable pageable);
    Page<CollabPost> findAllByCategories(Pageable pageable, List<Long> categories);
    CollabPost findCollabPostDetails(Long collabPostId);
    Optional<CollabPost> findByIdWithMember(Long collabPostId);

}
