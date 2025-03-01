package com.partnerd.service.kafkaService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.partnerd.converter.ChatConverter;
import com.partnerd.domain.chat.ChatMessage;
import com.partnerd.repository.chatRoomRepository.ChatMessageRepository;
import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ChatMessageRepository chatMessageRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "#{'${spring.kafka.topic.chat}'}",
            groupId = "#{'${spring.kafka.consumer.group-id}'}", concurrency = "10")
    public void consumeChatMessage(ConsumerRecord<String, Message> record, Acknowledgment acknowledgment) {
        {
            Message message = record.value();
            log.info("📩 Kafka 메시지 수신: {}", message);
            try {
                System.out.println("Received message from Kafka: " + message);

                ChatMessage chatMessage = ChatMessage.builder()
                        .chatRoomId(message.getChatRoomId())
                        .contentType(message.getContentType())
                        .content(message.getContent())
                        .senderNickname(message.getSenderNickname())
                        .sendDateTime(Instant.now()) // ISODate 형식으로 저장됨 (UTC)
                        .readCount(message.getReadCount())
                        .build();

                // MongoDB 에 저장
                chatMessageRepository.save(chatMessage);


                ChatDTO.ChatResponseDTO chatResponseDTO = ChatConverter.toChatResponseDTO(chatMessage);
                // WebSocket을 통해 특정 채팅방으로 메시지 전달
                // Redis Pub/Sub으로 WebSocket 메시지 전송
                // ✅ 메시지를 Redis Pub/Sub으로 발행
                String jsonMessage = objectMapper.writeValueAsString(message);
                redisTemplate.convertAndSend("chat-room:" + message.getChatRoomId(), jsonMessage);

                //  메시지 정상 처리 후 Kafka에 Offset 커밋
                acknowledgment.acknowledge();
                System.out.println("WebSocket을 통해 특정 채팅방으로 메시지 전달: " + chatResponseDTO);
            } catch (Exception e) {
                System.err.println("Error in KafkaListener: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
