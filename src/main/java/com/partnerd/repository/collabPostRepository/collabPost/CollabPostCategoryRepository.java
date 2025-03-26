package com.partnerd.repository.collabPostRepository.collabPost;

import com.partnerd.domain.CollabPost;
import com.partnerd.domain.mapping.CollabPostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollabPostCategoryRepository extends JpaRepository<CollabPostCategory, Long> {

    List<CollabPostCategory> findAllByCategoryIdAndCollabPost(Long categoryId, CollabPost collabPost);

}
