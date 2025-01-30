package com.partnerd.service.notifyService.listener;

import com.partnerd.service.notifyService.NotificationService;
import com.partnerd.service.notifyService.event.CollabAskEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CollabAskListener {

    private final NotificationService notificationService;

    @TransactionalEventListener
    @Async
    public void handleCollabAskEvent(CollabAskEvent event) {
        // 알림 전송 로직
        String message = event.getClubName() + " 으로부터 '" + event.getCollabPostTitle() + "' 에 대하여 협업요청을 받았습니다.";
        notificationService.sendNotification(event.getReceiverId(), message);
    }
}

