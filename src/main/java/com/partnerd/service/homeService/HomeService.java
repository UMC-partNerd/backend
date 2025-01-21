package com.partnerd.service.homeService;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.web.dto.homeDTO.response.HomeClubDTO;
import com.partnerd.web.dto.homeDTO.response.HomeCollabPostDTO;
import com.partnerd.web.dto.homeDTO.response.HomeProjectDTO;
import com.partnerd.web.dto.homeDTO.response.HomePromotionProjectDTO;

import java.util.List;
import java.util.Map;

public interface HomeService {
    List<HomeCollabPostDTO> getRecentCollabPosts(); //콜라보레이션 최신순조회

    List<HomeClubDTO> getPopularClubs(); //동아리 인기순 조회

    List<HomeProjectDTO> getRecentProjects();  //최근 프로젝트 모집글 조회

    List<HomePromotionProjectDTO> getPopularPromotionProjects(); //인기 프로젝트 홍보글 조회


}
