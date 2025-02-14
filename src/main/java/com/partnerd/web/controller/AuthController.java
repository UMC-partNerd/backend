package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.service.auth.OAuthService;
import com.partnerd.service.auth.RegisterService;
import com.partnerd.service.auth.TokenManagementService;
import com.partnerd.web.dto.authDTO.LoginResponseDTO;
import com.partnerd.web.dto.authDTO.TokenRefreshRequestDTO;
import com.partnerd.web.dto.authDTO.TokenRefreshResponseDTO;
import com.partnerd.web.dto.registerDTO.RegisterRequestDTO;
import com.partnerd.web.dto.registerDTO.RegisterResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "로그인, 회원가입 관련 API")
public class AuthController {

    private final OAuthService kakaoOAuthService;
    private final RegisterService registerService;
    private final TokenManagementService tokenManagementService;

    @GetMapping("/api/auth/login/kakao")
    @Operation(summary = "카카오 로그인", description = "카카오 인가 코드를 사용하여 로그인")
    public ApiResponse<LoginResponseDTO> loginWithKakao(
            @Parameter(description = "카카오에서 발급된 인가 코드", required = true)
            @RequestParam(name = "code") String code,
            HttpServletRequest request) {
        LoginResponseDTO response = kakaoOAuthService.login(code);
        // ✅ 현재 세션 가져오기 (없으면 생성)
        HttpSession session = request.getSession(true);

        // ✅ 세션에 사용자 정보 저장
        session.setAttribute("user", response.getId());
        session.setAttribute("sessionId", session.getId());

        response.setSessionId(session.getId());

        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/api/auth/register")
    @Operation(summary = "사용자 추가 정보 등록", description = "로그인 후 사용자의 추가 정보를 등록")
    public ApiResponse<RegisterResponseDTO> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "사용자 등록 요청 정보", required = true)
            @RequestBody @Valid RegisterRequestDTO request,
            @Parameter(description = "Bearer 토큰", required = true, example = "Bearer <JWT>")
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        RegisterResponseDTO response = registerService.registerUser(request, token);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/api/auth/token/refresh")
    @Operation(summary = "JWT 토큰 갱신", description = "리프레시 토큰을 사용하여 새로운 JWT를 발급합니다.")
    public ApiResponse<TokenRefreshResponseDTO> refreshJwtToken(
            @RequestBody @Valid TokenRefreshRequestDTO request) {
        String newJwtToken = tokenManagementService.refreshJwtToken(request.getExpiredToken());
        return ApiResponse.onSuccess(TokenRefreshResponseDTO.builder()
                .jwtToken(newJwtToken)
                .build());
    }
}
