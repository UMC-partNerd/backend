package com.partnerd.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {


    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "chat-topic", groupId = "chat-group", autoStartup = "false")
    public void consumeChatMessage(Message message) {
        System.out.println("Received message from Kafka: " + message);

        // ✅ WebSocket을 통해 특정 채팅방으로 메시지 전달
        messagingTemplate.convertAndSend("/subscribe/chat/" + message.getChatRoomId(), message);
    }
}
