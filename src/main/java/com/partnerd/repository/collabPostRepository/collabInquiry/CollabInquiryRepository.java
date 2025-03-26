package com.partnerd.repository.collabPostRepository.collabInquiry;

import com.partnerd.domain.CollabInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollabInquiryRepository extends JpaRepository<CollabInquiry, Long>, CollabInquiryRepositoryCustom{

    @Query("SELECT ci FROM CollabInquiry ci LEFT JOIN FETCH ci.member WHERE ci.id = :id")
    Optional<CollabInquiry> findByIdWithMember(@Param("id") Long id);

    @Query("SELECT ci FROM CollabInquiry ci " +
            "LEFT JOIN FETCH ci.children " +
            "LEFT JOIN FETCH ci.member " +
            "WHERE ci.id = :id AND ci.parentInquiry IS NULL")
    Optional<CollabInquiry> findByIdWithParentIdIsNULL(@Param("id") Long id);


    @Query("SELECT DISTINCT ci FROM CollabInquiry ci " +
            "LEFT JOIN FETCH ci.parentInquiry cp " +
            "LEFT JOIN FETCH ci.children cc " +
            "LEFT JOIN FETCH ci.member " +
            "WHERE ci.id = :id AND ci.parentInquiry IS NOT NULL")
    Optional<CollabInquiry> findByIdWithParentIdIsNOTNULL(@Param("id") Long id);


}
