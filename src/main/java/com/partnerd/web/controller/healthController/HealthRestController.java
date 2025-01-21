package com.partnerd.web.controller.healthController;

import com.partnerd.apiPaylaod.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthRestController {


    @GetMapping("")
    @Operation(summary = "서버 health chaeck용 API",description = "서버 health check를 위한 API입니다. (프론트와 관계 없음)")
    public ApiResponse<String> healthAPI(){

        return ApiResponse.onSuccess("health!");
    }

}