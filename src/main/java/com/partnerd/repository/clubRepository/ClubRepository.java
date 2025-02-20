package com.partnerd.repository.clubRepository;

import com.partnerd.domain.Club;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryCustom{
    List<Club> findTop4ClubByOrderByViewsDesc();

    // 전체 인기순 정렬
    Page<Club> findAllByOrderByViewsDesc(Pageable pageable);

    // 전체 최신순 정렬
    Page<Club> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 특정 카테고리 인기순 정렬
    Page<Club> findByCategoryIdOrderByViewsDesc(Long categoryId, Pageable pageable);

    // 특정 카테고리 최신순 정렬
    Page<Club> findByCategoryIdOrderByCreatedAtDesc(Long categoryId, Pageable pageable);

    //조회수 증가
    @Modifying
    @Transactional
    @Query("UPDATE Club c SET c.views = c.views + 1 WHERE c.id = :clubId")
    void incrementViews(@Param("clubId") Long clubId);


}
