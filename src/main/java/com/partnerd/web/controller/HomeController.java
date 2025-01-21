package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.service.homeService.HomeService;
import com.partnerd.web.dto.homeDTO.response.HomeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping()
    ApiResponse<HomeResponseDTO> getHomeData(){
        HomeResponseDTO responseDTO = new HomeResponseDTO(
                homeService.getRecentCollabPosts(),
                homeService.getPopularClubs(),
                homeService.getRecentProjects(),
                homeService.getPopularPromotionProjects()
        );

        return ApiResponse.onSuccess(responseDTO);
    }





}
