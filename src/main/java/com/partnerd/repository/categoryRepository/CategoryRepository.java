package com.partnerd.repository.categoryRepository;

import com.partnerd.mongoRepository.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    @Query("SELECT c FROM Category c WHERE c.id IN :categoryIds")
    List<Category> findAllById(@Param("categoryIds") List<Long> categoryIds);

}
