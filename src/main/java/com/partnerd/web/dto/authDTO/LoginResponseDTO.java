package com.partnerd.web.dto.authDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String jwtToken; // JWT 토큰
    private Long id; // 사용자 ID
    private String email; // 닉네임
}
