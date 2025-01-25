package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.CollabPost;
import com.partnerd.domain.CollabPostImg;
import com.partnerd.web.dto.homeDTO.response.HomeCollabPostDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CollabPostRepository extends JpaRepository<CollabPost, Long>, CollabPostRepositoryCustom {
  
    List<CollabPost> findTop4ByOrderByCreatedAtDesc();

    @Query("SELECT cp FROM CollabPost cp " +
            "LEFT JOIN FETCH cp.collabPostImgList "+
            "WHERE cp.id = :id")
    CollabPost findByIdWithCollabPostImg(@Param("id") Long collabPostId);

  
}
