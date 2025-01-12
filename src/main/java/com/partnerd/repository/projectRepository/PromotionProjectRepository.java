package com.partnerd.repository.projectRepository;


import com.partnerd.domain.PromotionProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionProjectRepository extends JpaRepository<PromotionProject, Long>, PromotionProjectRepositoryCustiom {
}
