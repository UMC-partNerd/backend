package com.partnerd.service.notifyService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.domain.Member;
import com.partnerd.domain.Notification;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.notificationRepository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final EmitterRepository emitterRepository;
    private final MemberRepository memberRepository;

    // subscribe 로 연결 요청 시 SseEmitter(발신기)를 생성.
    @Override
    public SseEmitter subscribe(Long memberId, String lastEventId) {
        String emitterId = makeTimeIncludeId(memberId); // SseEmitter 를 구분,관리 하기 위한 식별자
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // SseEmitter 의 완료/시간초과/에러로 인한 전송 불가 시 sseEmitter 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeId(memberId);  // 개별 알림 이벤트를 식별하기 위한 고유한 값
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + memberId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (!lastEventId.isEmpty()) { // Last-Event-ID가 존재한다는 것은 받지 못한 데이터가 있다는 것이다.
            sendLostData(lastEventId, memberId, emitterId, emitter);
        }


        return emitter;
    }

    private String makeTimeIncludeId(Long memebrId) {  // 데이터 유실 시점 파악 위함
        return memebrId + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private void sendLostData(String lastEventId, Long memberId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));
        eventCaches.entrySet().stream() // 클라이언트의 요청 헤더에 lastEventId 값이 있는 경우 lastEventId 보다 더 큰(더 나중에 생성된) emitter를 찾아서 발송
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }


    @Override
    public void sendNotification(Long memberId, String message) {

        Member receiver = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Notification notification = Notification.builder()
                .receiver(receiver)
                .message(message)
                .isRead(false)
                .build();


        String receiverId = String.valueOf(memberId);
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, notification.getMessage());
                }
        );

    }

}
