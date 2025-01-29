package com.partnerd.repository.projectRepository;


import com.partnerd.domain.PromotionProject;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionProjectRepositoryCustiom {

    // 프로젝트 홍보글 상세페이지 조회
    PromotionProject findPromotionProjectDetails(Long promotionProjectId);

    // 프로젝트 홍보글 모아보기 (인기순/최신순)
    Page<PromotionProject> getPromotionProjectList(Integer page, Integer sort);

    // 프로젝트 홍보글 모아보기 (인기 top3)
    List<PromotionProject> getPromotionProjectTop3();

    // 마이페이지 - 내가 쓴 프로젝트 홍보글 모아보기
    List<PromotionProject> findPromotionProjectsByMemberId(Long memberId);
}
