package com.partnerd.web.dto.authDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRefreshRequestDTO {
    @NotBlank(message = "만료된 JWT 토큰은 필수입니다.")
    private String expiredToken;
}
