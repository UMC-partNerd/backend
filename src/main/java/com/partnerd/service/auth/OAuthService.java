package com.partnerd.service.auth;

import com.partnerd.web.dto.authDTO.LoginResponseDTO;

public interface OAuthService {
    LoginResponseDTO login(String code); // 공통 로그인 메서드
}
