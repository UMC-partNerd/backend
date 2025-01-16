package com.partnerd.service.homeService;

import com.partnerd.apiPaylaod.ApiResponse;

import java.util.Map;

public interface HomeService {
    ApiResponse<Map<String, Object>> getHomeData();
}
