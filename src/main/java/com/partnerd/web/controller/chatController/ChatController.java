package com.partnerd.web.controller.chatController;

import com.partnerd.kafka.KafkaProducer;
import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final KafkaProducer kafkaProducer;
    @MessageMapping("/send")
    public ResponseEntity<String> sendChatMessage(@RequestBody ChatDTO chatDTO) {
        // Kafka로 메시지 전송
        kafkaProducer.sendMessage(chatDTO);
        return ResponseEntity.ok("Message sent to Kafka!");
    }
}
