package com.partnerd.service.notifyService;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
   SseEmitter subscribe(Long memberId, String lastEventId);
   void sendNotification(Long memberId, String content);
}
