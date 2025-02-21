package com.partnerd.repository.collabPostRepository.collabPost;

import com.partnerd.domain.CollabPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CollabPostRepository extends JpaRepository<CollabPost, Long>, CollabPostRepositoryCustom {

    @Query("SELECT cp FROM CollabPost cp LEFT JOIN FETCH cp.collabInquiryList ci WHERE cp.id = :id")
    Optional<CollabPost> findByIdWithInquiry(@Param("id") Long collabPostId);

    List<CollabPost> findTop4ByOrderByCreatedAtDesc();

    @Query("SELECT cp FROM CollabPost cp " +
            "LEFT JOIN FETCH cp.collabPostImgList "+
            "WHERE cp.id = :id")
    CollabPost findByIdWithCollabPostImg(@Param("id") Long collabPostId);

    @Modifying
    @Query(value = "DELETE FROM collab_post WHERE id > :cutoffId", nativeQuery = true)
    void deleteAllPostsById(@Param("cutoffId") Long cutoffId);


}
