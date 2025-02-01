package com.partnerd.service.homeService.impl;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.domain.Club;
import com.partnerd.domain.ClubImage;
import com.partnerd.domain.ProjectImage;
import com.partnerd.domain.PromotionProjectImage;
import com.partnerd.domain.enums.ImageType;
import com.partnerd.repository.clubRepository.ClubRepository;
import com.partnerd.repository.clubRepository.ClubRepositoryCustom;
import com.partnerd.repository.collabPostRepository.CollabPostRepository;
import com.partnerd.repository.collabPostRepository.CollabPostRepositoryCustom;
import com.partnerd.repository.projectRepository.ProjectRepository;
import com.partnerd.repository.projectRepository.ProjectRepositoryCustiom;
import com.partnerd.repository.projectRepository.PromotionProjectRepository;
import com.partnerd.repository.projectRepository.PromotionProjectRepositoryCustiom;
import com.partnerd.service.homeService.HomeService;
import com.partnerd.web.dto.homeDTO.response.HomeClubDTO;
import com.partnerd.web.dto.homeDTO.response.HomeCollabPostDTO;
import com.partnerd.web.dto.homeDTO.response.HomeProjectDTO;
import com.partnerd.web.dto.homeDTO.response.HomePromotionProjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final CollabPostRepository collabPostRepository;
    private final ClubRepository clubRepository;
    private final ProjectRepository projectRepository;
    private final PromotionProjectRepository promotionProjectRepository;





    /**
     * ✅ 인기 동아리 3개 조회 (QueryDSL 적용)
     */
    @Override
    public List<HomeClubDTO> getPopularClubs() {
        Pageable topThree = PageRequest.of(0, 3);
        return clubRepository.findTopClubs(topThree);
    }


    /**
     * ✅ 최신 콜라보 모집글 4개 조회 (QueryDSL 적용)
     */
    @Override
    public List<HomeCollabPostDTO> getRecentCollabPosts(){
        Pageable topFour = PageRequest.of(0, 4);
        return collabPostRepository.findTopCollabPosts(topFour);
    }


    /**
     * ✅ 최신 프로젝트 6개 조회 (QueryDSL 적용)
     */
    @Override
    public List<HomeProjectDTO> getRecentProjects(){
        Pageable topSix = PageRequest.of(0, 6);
        return projectRepository.findTopProjects(topSix);
    }

    /**
     * 인기 프로젝트 홍보글 6개 조회
     */

    @Override
    public List<HomePromotionProjectDTO> getPopularPromotionProjects(){
        Pageable topSix = PageRequest.of(0, 6);
        return promotionProjectRepository.findTopPromotionProjects(topSix);
    }


}
