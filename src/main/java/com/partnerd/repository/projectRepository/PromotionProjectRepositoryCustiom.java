package com.partnerd.repository.projectRepository;


import com.partnerd.domain.PromotionProject;

public interface PromotionProjectRepositoryCustiom {

    // 프로젝트 홍보글 상세페이지 조회
    PromotionProject findPromotionProjectDetails(Long promotionProjectId);
}
