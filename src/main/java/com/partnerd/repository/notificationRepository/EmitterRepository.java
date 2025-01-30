package com.partnerd.repository.notificationRepository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;


public interface EmitterRepository {

    // Emitter를 저장 (발신기 저장)
    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    // 이벤트 (알림 저장)
    void saveEventCache(String emitterId, Object event);

    // 해당 회원과 관련된 모든 Emitter를 찾는다.
    // SSE는 브라우저가 서버와 1:N 연결을 허용한다. 즉, 브라우저에서 각 탭, 창, 디바이스는 별도로 서버와 연결하고 독립된 Emitter를 생성한다.
    // 브라우저당 여러 개 연결이 가능하기에 여러 Emitter가 존재할 수 있다. 따라서 모든 연결로 동일한 알림을 보내야 하기 때문에 해당 메서드가 필요함.
    Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId);

    // 해당 회원과 관련된 모든 이벤트를 찾는다
    Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId);

    // 해당 Emitter 를 지운다.
    void deleteById(String id);

    // 해당 회원과 관련된 모든 Emitter를 지운다.
    void deleteAllEmitterStartWithId(String memberId);
    // 해당 회원과 관련된 모든 이벤트를 지운다.
    void deleteAllEventCacheStartWithId(String memberId);
}
