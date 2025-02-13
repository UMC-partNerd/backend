package com.partnerd.service.kafkaService;

import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducer {


    private final KafkaTemplate<String, Message> kafkaTemplate;

    public void sendMessage(ChatDTO.ChatRequestDTO chatDTO) {

        Instant now = Instant.now();  // ✅ 현재 UTC 시간
        LocalDateTime sendDateTime = LocalDateTime.ofInstant(now, ZoneId.of("Asia/Seoul"));  // ✅ KST 변환

        // 메시지 객체 생성
       Message message = Message.builder()
                .id(UUID.randomUUID().toString())  // 메시지 ID 자동 생성
                .chatRoomId(chatDTO.getChatRoomId())
                .contentType("text")  // 기본 텍스트 타입
                .content(chatDTO.getContent())
                .senderNickname(chatDTO.getSenderNickname())
                .sendDateTime(sendDateTime)
                .readCount(0) // 초기 읽음 카운트 0
                .build();

       /**
        *  roomId를 Key로 설정하면 Kafka가 자동으로 동일한 roomId 메시지를 같은 파티션으로 보냄
        *
        * ✔ 파티셔닝 전략
        * Kafka 기본 파티셔너(DefaultPartitioner)가 key.hash() % partition 개수 로 파티션을 배정함
        * */

        System.out.println("카프카로 메세지 보내기!");
        System.out.println("[Kafka Producer]: " + message);
        kafkaTemplate.send("chat-topic", String.valueOf(chatDTO.getChatRoomId()), message);
    }


}
