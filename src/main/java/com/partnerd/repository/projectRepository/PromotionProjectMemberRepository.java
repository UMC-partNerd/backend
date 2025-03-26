package com.partnerd.repository.projectRepository;

import com.partnerd.mongoRepository.domain.PromotionProject;
import com.partnerd.mongoRepository.domain.mapping.PromotionProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionProjectMemberRepository extends JpaRepository<PromotionProjectMember, Long> {
    void deleteByPromotionProject(PromotionProject promotionProject);
}