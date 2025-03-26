package com.partnerd.service.auth;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.TokenHandler;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.domain.RefreshToken;
import com.partnerd.repository.refreshTokenRepository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TokenManagementService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String refreshJwtToken(String expiredToken) {
        // JWT 토큰의 유효성 확인 (만료된 토큰은 통과시킴)
        if (!jwtTokenProvider.validateTokenIgnoringExpiration(expiredToken)) {
            throw new TokenHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        // JWT 토큰에서 사용자 ID 추출
        Long userId = Long.valueOf(jwtTokenProvider.getClaims(expiredToken).getSubject());

        // Redis에서 사용자 ID 기반 리프레시 토큰 확인 (StreamSupport 사용)
        Optional<RefreshToken> refreshTokenOptional = StreamSupport.stream(refreshTokenRepository.findAll().spliterator(), false)
                .filter(token -> token.getUserId().equals(userId))
                .findFirst();

        if (refreshTokenOptional.isEmpty()) {
            throw new TokenHandler(ErrorStatus.REFRESH_TOKEN_EXPIRED);
        }

        // Redis에서 리프레시 토큰이 유효한지 확인
        RefreshToken refreshToken = refreshTokenOptional.get();
        if (refreshToken == null || refreshToken.getId() == null) {
            throw new TokenHandler(ErrorStatus.REFRESH_TOKEN_EXPIRED);
        }

        // 새로운 JWT 토큰 생성
        return jwtTokenProvider.createToken(userId, jwtTokenProvider.getClaims(expiredToken).get("nickname", String.class));
    }
}
