package com.partnerd.repository.collabPostRepository.collabPost;

import com.partnerd.domain.Category;
import com.partnerd.domain.CollabPost;
import com.partnerd.domain.mapping.CollabPostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollabPostCategoryRepository extends JpaRepository<CollabPostCategory, Long> {

    List<CollabPostCategory> findAllByCategory_IdAndCollabPost(Long categoryId, CollabPost collabPost);

}
