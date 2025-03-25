package com.partnerd.service.homeService.impl;

import com.partnerd.repository.clubRepository.ClubRepository;
import com.partnerd.repository.collabPostRepository.collabPost.CollabPostRepository;
import com.partnerd.repository.projectRepository.ProjectRepository;
import com.partnerd.repository.projectRepository.PromotionProjectRepository;
import com.partnerd.r2dbc.ClubR2DBCRepositoryCustom;
import com.partnerd.r2dbc.CollabPostR2DBCRepositoryCustom;
import com.partnerd.r2dbc.ProjectR2DBCRepositoryCustom;
import com.partnerd.r2dbc.PromotionProjectR2DBCRepositoryCustom;
import com.partnerd.service.homeService.HomeService;
import com.partnerd.web.dto.homeDTO.response.HomeClubDTO;
import com.partnerd.web.dto.homeDTO.response.HomeCollabPostDTO;
import com.partnerd.web.dto.homeDTO.response.HomeProjectDTO;
import com.partnerd.web.dto.homeDTO.response.HomePromotionProjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final CollabPostRepository collabPostRepository;
    private final ClubRepository clubRepository;
    private final ProjectRepository projectRepository;
    private final PromotionProjectRepository promotionProjectRepository;

    // R2DBC Repository
    private final CollabPostR2DBCRepositoryCustom collabPostR2DBCRepositoryCustom;
    private final ClubR2DBCRepositoryCustom clubR2DBCRepositoryCustom;
    private final ProjectR2DBCRepositoryCustom projectR2DBCRepositoryCustom;
    private final PromotionProjectR2DBCRepositoryCustom promotionProjectR2DBCRepositoryCustom;



    @Async
    public CompletableFuture<List<HomeCollabPostDTO>> getRecentCollabPosts() {
        return CompletableFuture.completedFuture(fetchRecentCollabPosts());
    }

    @Async
    public CompletableFuture<List<HomeClubDTO>> getPopularClubs() {
        return CompletableFuture.completedFuture(fetchPopularClubs());
    }

    @Async
    public CompletableFuture<List<HomeProjectDTO>> getRecentProjects() {
        return CompletableFuture.completedFuture(fetchRecentProjects());
    }

    @Async
    public CompletableFuture<List<HomePromotionProjectDTO>> getPopularPromotionProjects() {
        return CompletableFuture.completedFuture(fetchPopularPromotionProjects());
    }

    @Override
    public Mono<List<HomeCollabPostDTO>> getRecentCollabPostsByAsync() {
        return collabPostR2DBCRepositoryCustom.findTopCollabPosts(4)
                .collectList();
    }

    @Override
    public Mono<List<HomeClubDTO>> getPopularClubsByAsync() {
        return clubR2DBCRepositoryCustom.findTopClubs(3)
                .collectList();
    }

    @Override
    public Mono<List<HomeProjectDTO>> getRecentProjectsByAsync() {

        return projectR2DBCRepositoryCustom.findTopProjects(6)
                .collectList();
    }

    @Override
    public Mono<List<HomePromotionProjectDTO>> getPopularPromotionProjectsByAsync() {
        return promotionProjectR2DBCRepositoryCustom.findTopPromotionProjects(6)
                .collectList();
    }


    /**
     * ✅ 인기 동아리 3개 조회 (QueryDSL 적용)
     */

    public List<HomeClubDTO> fetchPopularClubs() {
        Pageable topThree = PageRequest.of(0, 3);
        return clubRepository.findTopClubs(topThree);
    }


    /**
     * ✅ 최신 콜라보 모집글 4개 조회 (QueryDSL 적용)
     */

    public List<HomeCollabPostDTO> fetchRecentCollabPosts(){
        Pageable topFour = PageRequest.of(0, 4);
        return collabPostRepository.findTopCollabPosts(topFour);
    }


    /**
     * ✅ 최신 프로젝트 6개 조회 (QueryDSL 적용)
     */

    public List<HomeProjectDTO> fetchRecentProjects(){
        Pageable topSix = PageRequest.of(0, 6);
        return projectRepository.findTopProjects(topSix);
    }

    /**
     * 인기 프로젝트 홍보글 6개 조회
     */


    public List<HomePromotionProjectDTO> fetchPopularPromotionProjects(){
        Pageable topSix = PageRequest.of(0, 6);
        return promotionProjectRepository.findTopPromotionProjects(topSix);
    }


}
