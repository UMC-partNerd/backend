package com.partnerd.repository.projectRepository;


import com.partnerd.mongoRepository.domain.PromotionProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionProjectRepository extends JpaRepository<PromotionProject, Long>, PromotionProjectRepositoryCustiom {

    List<PromotionProject> findTop6ByOrderByViewsDesc();
}
