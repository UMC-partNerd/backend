package com.partnerd.service.token;

import com.partnerd.mongoRepository.domain.RefreshToken;
import com.partnerd.repository.refreshTokenRepository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service // ✅ 주석 해제
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, String> redisTemplate;

    // Refresh Token 저장 (RedisRepository 사용)
    public void saveRefreshToken(String refreshToken, Long userId) {
        RefreshToken token = new RefreshToken(refreshToken, userId);
        refreshTokenRepository.save(token);
        System.out.println("✅ Saved RefreshToken in Redis: Key=" + token.getId() + ", Value=" + token);
    }

    // Refresh Token 저장 (RedisTemplate 사용 - CrudRepository 대신)
    public void saveRefreshTokenWithTemplate(String refreshToken, Long userId) {
        redisTemplate.opsForValue().set(refreshToken, String.valueOf(userId), 30, TimeUnit.DAYS);
        System.out.println("✅ Saved in RedisTemplate: Key=" + refreshToken + ", Value=" + userId);
    }

    // Refresh Token 조회
    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Refresh Token"));
    }

    // Refresh Token 삭제
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
        redisTemplate.delete(refreshToken);
        System.out.println("✅ Deleted RefreshToken: " + refreshToken);
    }
}
