package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.service.homeService.HomeService;
import com.partnerd.web.dto.homeDTO.response.HomeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping()
    @Operation(summary = "홈 데이터 조회 API", description = "홈 화면에 필요한 데이터를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공적으로 데이터를 조회했습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.")
    })
    public ApiResponse<HomeResponseDTO> getHomeData() {
        HomeResponseDTO responseDTO = new HomeResponseDTO(
                homeService.getRecentCollabPosts(),
                homeService.getPopularClubs(),
                homeService.getRecentProjects(),
                homeService.getPopularPromotionProjects()
        );

        return ApiResponse.onSuccess(responseDTO);
    }
}