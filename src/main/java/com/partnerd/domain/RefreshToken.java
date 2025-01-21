package com.partnerd.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 14400) // TTL 4시간
public class RefreshToken {

    @Id
    private String id; // Redis의 고유 키
    private Long userId; // 사용자 ID

    public RefreshToken(String refreshToken, Long userId) {
        this.id = refreshToken; // Redis 키는 그대로 refreshToken 값
        this.userId = userId;
    }
}
