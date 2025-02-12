package com.partnerd.repository.projectRepository;


import com.partnerd.domain.PromotionProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionProjectRepository extends JpaRepository<PromotionProject, Long>, PromotionProjectRepositoryCustom {

    List<PromotionProject> findTop6ByOrderByViewsDesc();
}
