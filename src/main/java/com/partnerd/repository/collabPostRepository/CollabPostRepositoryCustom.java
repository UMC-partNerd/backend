package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.CollabPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollabPostRepositoryCustom {
    Page<CollabPost> findAllWithCategories(Pageable pageable);
    CollabPost findCollabPostDetails(Long collabPostId);
}
