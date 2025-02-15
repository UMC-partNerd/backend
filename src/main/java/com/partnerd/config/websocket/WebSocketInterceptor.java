package com.partnerd.config.websocket;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
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

        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

            // ✅ 1. Query parameter 로 `Session-Id` 가져오기
            String sessionId = servletRequest.getParameter("sessionId");
            System.out.println("🚀 전달된 Session-Id: " + servletRequest.getParameter("sessionId"));
            System.out.println("🔍 WebSocket 요청 - 전달된 Session-Id: " + sessionId);

            if (sessionId == null) {
                System.out.println("❌ WebSocket 요청에 Session-Id가 없음");
                return false;
            }
                // ✅ Redis에서 해당 세션이 존재하는지 확인
                Session redisSession = sessionRepository.findById(sessionId);
                System.out.println("📡 Redis 세션 조회 결과: " + redisSession);

                if (redisSession != null) {
                    attributes.put("sessionId", sessionId); // ✅ WebSocket 세션에 저장
                    System.out.println("✅ WebSocket 세션 인증 성공: " + sessionId);
                    return true;
                }

            // ✅ 3. WebSocket 세션에 저장
            attributes.put("sessionId", sessionId);
            System.out.println("✅ WebSocket 세션 인증 성공: " + sessionId);
            return true;
        }


        System.out.println("❌ WebSocket 세션 인증 실패");
        return false; // 핸드셰이크 중단
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 핸드셰이크 이후 실행 (옵션)
    }
}
