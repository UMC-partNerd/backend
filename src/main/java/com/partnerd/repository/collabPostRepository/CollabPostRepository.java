package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.CollabPost;
import com.partnerd.web.dto.homeDTO.response.HomeCollabPostDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollabPostRepository extends JpaRepository<CollabPost, Long>, CollabPostRepositoryCustom {
  
    List<CollabPost> findTop4ByOrderByCreatedAtDesc();
  
}
