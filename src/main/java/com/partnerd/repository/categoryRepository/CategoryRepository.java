package com.partnerd.repository.categoryRepository;

import com.partnerd.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
}
