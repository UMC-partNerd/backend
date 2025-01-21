package com.partnerd.service.projectService;

import com.partnerd.domain.PromotionProject;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import org.springframework.data.domain.Page;

public interface PromotionProjectService {

    // 프로젝트 홍보 생성
    PromotionProject addPromotionProject(PromotionProjectRequestDTO.CreatePromotionProjectDTO request);

    // 프로젝트 홍보 수정
    PromotionProject updatePromotionProject(PromotionProjectRequestDTO.UpdatePromotionProjectDTO request, Long promotionProjectId);

    // 프로젝트 홍보 삭제
    Void deletePromotionProject(Long promotionProjectId);
    
    // 프로젝트 홍보글 모아보기
    Page<PromotionProject> getPromotionProjectList(Integer page, Integer sort);
}
