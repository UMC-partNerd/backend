package com.partnerd.service.auth;

import com.partnerd.web.dto.auth.LoginResponse;

public interface OAuthService {
    LoginResponse login(String code); // 공통 로그인 메서드
}
