package com.partnerd.repository.projectRepository;


import com.partnerd.domain.PromotionProject;

import java.util.List;

public interface PromotionProjectRepositoryCustiom {

    // 프로젝트 홍보글 상세페이지 조회
    PromotionProject findPromotionProjectDetails(Long promotionProjectId);

    // 마이페이지 - 내가 쓴 프로젝트 홍보글 모아보기
    List<PromotionProject> findPromotionProjectsByMemberId(Long memberId);
}
