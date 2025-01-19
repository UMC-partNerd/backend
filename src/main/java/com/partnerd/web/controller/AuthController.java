package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.code.status.SuccessStatus;
import com.partnerd.service.auth.OAuthService;
import com.partnerd.service.auth.RegisterService;
import com.partnerd.web.dto.authDTO.LoginResponseDTO;
import com.partnerd.web.dto.registerDTO.RegisterRequestDTO;
import com.partnerd.web.dto.registerDTO.RegisterResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuthService kakaoOAuthService;
    private final RegisterService registerService;

    @GetMapping("/api/auth/login/kakao")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> loginWithKakao(@RequestParam String code) {
        try {
            LoginResponseDTO response = kakaoOAuthService.login(code);
            return ResponseEntity.ok(ApiResponse.of(SuccessStatus._OK, response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.onFailure(
                    ErrorStatus._BAD_REQUEST.getCode(),
                    "유효하지 않은 인가 코드입니다.",
                    null
            ));
        }
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<ApiResponse<RegisterResponseDTO>> registerUser(
            @RequestBody @Valid RegisterRequestDTO request,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            RegisterResponseDTO response = registerService.registerUser(request, token);
            return ResponseEntity.ok(ApiResponse.of(SuccessStatus._OK, response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.onFailure(
                    ErrorStatus._BAD_REQUEST.getCode(),
                    "추가 정보 등록 중 오류가 발생했습니다.",
                    null
            ));
        }
    }
}
