package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.CollabPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CollabPostRepositoryCustom {
    Page<CollabPost> findAllWithCategories(Pageable pageable);
    CollabPost findCollabPostDetails(Long collabPostId);
    Optional<CollabPost> findByIdWithMember(Long collabPostId);

}
