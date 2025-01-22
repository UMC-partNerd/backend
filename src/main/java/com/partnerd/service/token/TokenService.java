package com.partnerd.service.token;

import com.partnerd.domain.RefreshToken;
import com.partnerd.repository.refreshTokenRepository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // Refresh Token 저장
    public void saveRefreshToken(String refreshToken, Long userId) {
        RefreshToken token = new RefreshToken(refreshToken, userId);
        refreshTokenRepository.save(token);

        //
        System.out.println("Saved RefreshToken in Redis: Key=" + token.getId() + ", Value=" + token);
    }

    // Refresh Token 조회
    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Refresh Token"));
    }

    // Refresh Token 삭제
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }
}
