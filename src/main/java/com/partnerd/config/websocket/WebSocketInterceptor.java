package com.partnerd.config.websocket;

import lombok.RequiredArgsConstructor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
public class WebSocketInterceptor implements HandshakeInterceptor {

    private final SessionRepository<? extends Session> sessionRepository; // Redis 세션 저장소

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // 세션 ID 가져오기
        String sessionId = request.getHeaders().getFirst("Session-Id");

        if (sessionId != null && sessionRepository.findById(sessionId) != null) {
            attributes.put("sessionId", sessionId); // WebSocket 세션에 추가
            return true;
        }
        System.out.println("WebSocket 세션 인증 실패");
        return false; // 핸드셰이크 중단
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 핸드셰이크 이후 실행 (옵션)
    }
}
