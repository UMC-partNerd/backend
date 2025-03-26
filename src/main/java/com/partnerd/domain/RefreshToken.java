package com.partnerd.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 2592000) // TTL 30일
public class RefreshToken {

    @Id
    private String id; // Redis의 키 (refreshToken 자체)

    private Long userId; // 사용자 ID

    public RefreshToken(String refreshToken, Long userId) {
        this.id = refreshToken; // ID 필드에 refreshToken 값 저장
        this.userId = userId;
    }
}
