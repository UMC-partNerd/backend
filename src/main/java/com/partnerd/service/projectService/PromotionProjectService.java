package com.partnerd.service.projectService;

import com.partnerd.domain.PromotionProject;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionProjectService {

    // 프로젝트 홍보 생성
    PromotionProject addPromotionProject(PromotionProjectRequestDTO.CreatePromotionProjectDTO request);

    // 프로젝트 홍보 수정
    PromotionProject updatePromotionProject(PromotionProjectRequestDTO.UpdatePromotionProjectDTO request, Long promotionProjectId);

    // 프로젝트 홍보 삭제
    Void deletePromotionProject(Long promotionProjectId);
    
    // 프로젝트 홍보글 모아보기 (인기순/최신순)
    Page<PromotionProject> getPromotionProjectList(Integer page, Integer sort);

    // 프로젝트 홍보글 모아보기 (인기 top3)
    List<PromotionProject> getPromotionProjectTop3();

    // 프로젝트 홍보 모아보기 (검색)
    Page<PromotionProject> getPromotionProjectSearchList(Integer page, String keyword);

    // 프로젝트 홍보글 상세페이지 조회
    PromotionProject getPromotionProject(Long promotionProjectId);

    // 마이페이지 - 내가 쓴 프로젝트 홍보글 모아보기
    List<PromotionProject> getMyPromotionProjects(Long memberId);
}
