package com.partnerd.kafka;

import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducer {


    private final KafkaTemplate<String, Message> kafkaTemplate;

    public void sendMessage(ChatDTO chatDTO) {
        // 메시지 객체 생성
        Message message = Message.builder()
                .id(UUID.randomUUID().toString())  // 메시지 ID 자동 생성
                .chatRoomId(chatDTO.getChatRoomId())
                .contentType("text")  // 기본 텍스트 타입
                .content(chatDTO.getContent())
                .senderNickName(chatDTO.getSenderNickName())
                .senderId(chatDTO.getSenderId())
                .collabAskId(chatDTO.getCollabAskId())
                .sendTime(Instant.now().toEpochMilli()) // 현재 시간 (밀리초)
                .readCount(0) // 초기 읽음 카운트 0
                .build();

        kafkaTemplate.send("chat",  String.valueOf(chatDTO.getChatRoomId()), message);
    }


}
