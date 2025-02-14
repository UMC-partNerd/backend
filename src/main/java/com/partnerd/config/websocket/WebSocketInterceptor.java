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
            HttpSession session = servletRequest.getSession(false);

            System.out.println(session);
            System.out.println(session.getId());

            if (session != null) {
                String sessionId = session.getId(); // ✅ HttpSession에서 세션 ID 가져오기
                System.out.println("🔍 HttpSession에서 가져온 sessionId: " + sessionId);

                // ✅ Redis에서 해당 세션이 존재하는지 확인
                Session redisSession = sessionRepository.findById(sessionId);
                System.out.println("📡 Redis 세션 조회 결과: " + redisSession);

                if (redisSession != null) {
                    attributes.put("sessionId", sessionId); // ✅ WebSocket 세션에 저장
                    System.out.println("✅ WebSocket 세션 인증 성공: " + sessionId);
                    return true;
                }
            }
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
