package com.partnerd.service.homeService.impl;

import com.partnerd.r2dbcRepository.ClubR2DBCRepositoryCustom;
import com.partnerd.r2dbcRepository.CollabPostR2DBCRepositoryCustom;
import com.partnerd.r2dbcRepository.ProjectR2DBCRepositoryCustom;
import com.partnerd.r2dbcRepository.PromotionProjectR2DBCRepositoryCustom;
import com.partnerd.repository.clubRepository.ClubRepository;
import com.partnerd.repository.collabPostRepository.collabPost.CollabPostRepository;
import com.partnerd.repository.projectRepository.ProjectRepository;
import com.partnerd.repository.projectRepository.PromotionProjectRepository;
import com.partnerd.service.homeService.HomeService;
import com.partnerd.web.dto.homeDTO.response.HomeClubDTO;
import com.partnerd.web.dto.homeDTO.response.HomeCollabPostDTO;
import com.partnerd.web.dto.homeDTO.response.HomeProjectDTO;
import com.partnerd.web.dto.homeDTO.response.HomePromotionProjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
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


    private final ReactiveRedisTemplate<String, Object> redisTemplate;


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
        String cacheKey = "home:recentCollabPosts";
        return  redisTemplate.opsForValue().get(cacheKey)
                .map(obj -> (List<HomeCollabPostDTO>) obj) // 타입 명시적 캐스팅
                .switchIfEmpty(collabPostR2DBCRepositoryCustom.findTopCollabPosts(4).collectList()
                        .flatMap(collabPosts -> redisTemplate.opsForValue()
                                .set(cacheKey, collabPosts)
                                .thenReturn(collabPosts)
                        )
                );
    }

    @Override
    public Mono<List<HomeClubDTO>> getPopularClubsByAsync() {
        String cacheKey = "home:popularClubs";
        return  redisTemplate.opsForValue().get(cacheKey)
                .map(obj -> (List<HomeClubDTO>) obj) // 타입 명시적 캐스팅
                .switchIfEmpty(clubR2DBCRepositoryCustom.findTopClubs(3).collectList()
                        .flatMap(clubs -> redisTemplate.opsForValue()
                                .set(cacheKey, clubs)
                                .thenReturn(clubs)
                        )
                );
    }

    /**
     * 1. 레디스로부터 저장된 키 값 확인
     * 2. 있으면 리턴
     * 3. 없으면 안의 로직을 실행.
     *
     * */
    @Override
    public Mono<List<HomeProjectDTO>> getRecentProjectsByAsync() {
        String cacheKey = "home:recentProjects";

        return redisTemplate.opsForValue().get(cacheKey)
                .map(obj -> (List<HomeProjectDTO>) obj) // 타입 명시적 캐스팅
                .switchIfEmpty(
                        projectR2DBCRepositoryCustom.findTopProjects(6).collectList()
                                .flatMap(projects -> redisTemplate.opsForValue()
                                        .set(cacheKey, projects)
                                        .thenReturn(projects)
                                )
                );
    }

    @Override
    public Mono<List<HomePromotionProjectDTO>> getPopularPromotionProjectsByAsync() {
        String cacheKey = "home:promotionProjects";
        return redisTemplate.opsForValue().get(cacheKey)
                .map(obj -> (List<HomePromotionProjectDTO>) obj) // 타입 명시적 캐스팅
                .switchIfEmpty(
                        promotionProjectR2DBCRepositoryCustom.findTopPromotionProjects(6).collectList()
                                .flatMap(promotionProjects -> redisTemplate.opsForValue()
                                        .set(cacheKey, promotionProjects)
                                        .thenReturn(promotionProjects)
                                )
                );
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
