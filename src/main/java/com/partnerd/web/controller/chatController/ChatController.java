package com.partnerd.web.controller.chatController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.partnerd.service.kafkaService.KafkaProducer;
import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final KafkaProducer kafkaProducer;
    @MessageMapping("/chat/{chatRoomId}")
    public void sendChatMessage(@Payload ChatDTO chatDTO) {
        System.out.println("kafka 전송 시이이작!");
        System.out.println(chatDTO);
        // Kafka로 메시지 전송
        kafkaProducer.sendMessage(chatDTO);

    }
}
