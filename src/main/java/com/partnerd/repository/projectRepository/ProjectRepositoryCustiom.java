package com.partnerd.repository.projectRepository;


import com.partnerd.domain.Project;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectRepositoryCustiom {
    
    // 프로젝트 모집글 상세페이지 조회
    Project findProjectDetails(Long projectId);

    // 프로젝트 모집글 모아보기
    Page<Project> getProjectList(Integer page, Integer status, List<Long> category, String keyword);

    // 마이페이지 - 내가 쓴 프로젝트 모집글 모아보기
    List<Project> findProjectsByMemberId(Long memberId);
}
