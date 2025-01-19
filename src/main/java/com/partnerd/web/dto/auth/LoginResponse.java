package com.partnerd.web.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String jwtToken; // JWT 토큰
    private Long id; // 사용자 ID
    private String nickname; // 닉네임
}
