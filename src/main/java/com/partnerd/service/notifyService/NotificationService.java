package com.partnerd.service.notifyService;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    public SseEmitter subscribe(Long memberId, String lastEventId);
    public void sendNotification(Long memberId, String content);
}
