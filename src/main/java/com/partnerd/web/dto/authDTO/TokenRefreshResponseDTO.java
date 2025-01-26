package com.partnerd.web.dto.authDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRefreshResponseDTO {
    private String jwtToken; // 새로 발급된 JWT 토큰
}
