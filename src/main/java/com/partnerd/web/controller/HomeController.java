package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.service.homeService.HomeService;
import com.partnerd.web.dto.homeDTO.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    @Operation(summary = "홈 데이터 조회 API", description = "홈 화면에 필요한 데이터를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공적으로 데이터를 조회했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.")
    })
    public CompletableFuture<ApiResponse<HomeResponseDTO>> getHomeData() {

        CompletableFuture<List<HomeCollabPostDTO>> collabPostsFuture = homeService.getRecentCollabPosts();
        CompletableFuture<List<HomeClubDTO>> clubsFuture = homeService.getPopularClubs();
        CompletableFuture<List<HomeProjectDTO>> projectsFuture = homeService.getRecentProjects();
        CompletableFuture<List<HomePromotionProjectDTO>> promotionsFuture = homeService.getPopularPromotionProjects();

        return CompletableFuture.allOf(collabPostsFuture, clubsFuture, projectsFuture, promotionsFuture)
                .thenApply(voidResult -> {
                    try {
                        HomeResponseDTO responseDTO = new HomeResponseDTO(
                                collabPostsFuture.get(),
                                clubsFuture.get(),
                                projectsFuture.get(),
                                promotionsFuture.get()
                        );
                        return ApiResponse.onSuccess(responseDTO);
                    } catch (Exception e) {
                        throw new RuntimeException("홈 데이터 조회 중 오류 발생", e);
                    }
                });
    }
    @GetMapping("/v2")
    @Operation(summary = "홈 데이터 조회 API", description = "홈 화면에 필요한 데이터를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공적으로 데이터를 조회했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.")
    })
    public Mono<ApiResponse<HomeResponseDTO>> getHomeDataWithWebFlux() {
        return Mono.zip(
                homeService.getRecentCollabPostsByAsync(),
                homeService.getPopularClubsByAsync(),
                homeService.getRecentProjectsByAsync(),
                homeService.getPopularPromotionProjectsByAsync()
        ).map(tuple -> {
            HomeResponseDTO responseDTO = new HomeResponseDTO(
                    tuple.getT1(),  // collabPosts
                    tuple.getT2(),  // clubs
                    tuple.getT3(),  // projects
                    tuple.getT4()   // promotions
            );
            return ApiResponse.onSuccess(responseDTO);
        }).onErrorResume(e -> {
            throw new RuntimeException("홈 데이터 조회 중 오류 발생", e);
        });
    }
}