package com.partnerd.repository.projectRepository;


import com.partnerd.domain.Project;

public interface ProjectRepositoryCustiom {
    
    // 프로젝트 모집글 상세페이지 조회
    Project findProjectDetails(Long projectId);
}
