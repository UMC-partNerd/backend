package com.partnerd.repository;

import com.partnerd.domain.PromotionProject;
import com.partnerd.domain.mapping.PromotionProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionProjectMemberRepository extends JpaRepository<PromotionProjectMember, Long> {
    void deleteByPromotionProject(PromotionProject promotionProject);
}