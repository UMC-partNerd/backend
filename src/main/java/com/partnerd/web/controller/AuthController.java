package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.code.status.SuccessStatus;
import com.partnerd.service.auth.OAuthService;
import com.partnerd.service.auth.RegisterService;
import com.partnerd.web.dto.authDTO.LoginResponseDTO;
import com.partnerd.web.dto.registerDTO.RegisterRequestDTO;
import com.partnerd.web.dto.registerDTO.RegisterResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "로그인, 회원가입 관련 API")
public class AuthController {

    private final OAuthService kakaoOAuthService;
    private final RegisterService registerService;

    @GetMapping("/api/auth/login/kakao")
    @Operation(summary = "카카오 로그인", description = "카카오 인가 코드를 사용하여 로그인")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> loginWithKakao(
            @Parameter(description = "카카오에서 발급된 인가 코드", required = true)
            @RequestParam String code) {
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
    @Operation(summary = "사용자 추가 정보 등록", description = "로그인 후 사용자의 추가 정보를 등록")
    public ResponseEntity<ApiResponse<RegisterResponseDTO>> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "사용자 등록 요청 정보", required = true)
            @RequestBody @Valid RegisterRequestDTO request,
            @Parameter(description = "Bearer 토큰", required = true, example = "Bearer <JWT>")
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
