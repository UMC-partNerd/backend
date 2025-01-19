package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.code.status.SuccessStatus;
import com.partnerd.service.auth.OAuthService;
import com.partnerd.web.dto.auth.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuthService kakaoOAuthService;

    @GetMapping("/api/auth/login/kakao")
    public ResponseEntity<ApiResponse<LoginResponse>> loginWithKakao(@RequestParam String code) {
        try {
            LoginResponse response = kakaoOAuthService.login(code);
            return ResponseEntity.ok(ApiResponse.of(SuccessStatus._OK, response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.onFailure(
                    ErrorStatus._BAD_REQUEST.getCode(),
                    "유효하지 않은 인가 코드입니다.",
                    null
            ));
        }
    }
}

