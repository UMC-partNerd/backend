package com.partnerd.web.dto.authDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String jwtToken; // JWT 토큰
    private Long id; // 사용자 ID
    private String email; // 이메일
    private boolean isNewUser; // 새로운 유저 여부 추가
    private String nickname; // 사용자 닉네임
}
