package com.partnerd.repository.projectRepository;


import com.partnerd.domain.Project;

import java.util.List;

public interface ProjectRepositoryCustiom {
    
    // 프로젝트 모집글 상세페이지 조회
    Project findProjectDetails(Long projectId);

    // 마이페이지 - 내가 쓴 프로젝트 모집글 모아보기
    List<Project> findProjectsByMemberId(Long memberId);
}
