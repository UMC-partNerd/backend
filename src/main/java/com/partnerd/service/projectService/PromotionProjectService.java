package com.partnerd.service.projectService;

import com.partnerd.domain.PromotionProject;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;

public interface PromotionProjectService {

    // 프로젝트 홍보 생성
    PromotionProject addPromotionProject(PromotionProjectRequestDTO.CreatePromotionProjectDTO request);
}
