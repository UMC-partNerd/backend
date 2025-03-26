package com.partnerd.repository.projectRepository;


import com.partnerd.mongoRepository.domain.Member;
import com.partnerd.mongoRepository.domain.Project;
import com.partnerd.web.dto.homeDTO.response.HomeProjectDTO;
import com.partnerd.web.dto.projectDTO.ProjectRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectRepositoryCustiom {

    // 프로젝트 팀원 검색
    List<Member> getMemberForProject(ProjectRequestDTO.FindProjectMemberDTO request);

        // 프로젝트 모집글 상세페이지 조회
    Project findProjectDetails(Long projectId);

    // 프로젝트 모집글 모아보기
    Page<Project> getProjectList(Integer page, Integer status, List<Long> category, String keyword);

    // 마이페이지 - 내가 쓴 프로젝트 모집글 모아보기
    List<Project> findProjectsByMemberId(Long memberId);

    //홈화면 - 최신 프로젝트 6개 조회
    List<HomeProjectDTO> findTopProjects(Pageable pageable);
}
