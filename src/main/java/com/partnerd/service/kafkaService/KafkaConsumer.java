package com.partnerd.service.kafkaService;

import com.partnerd.converter.ChatConverter;
import com.partnerd.domain.chat.ChatMessage;
import com.partnerd.repository.chatRoomRepository.ChatMessageRepository;
import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;

    @KafkaListener(topics = "#{'${spring.kafka.topic.chat}'}",
            groupId = "#{'${spring.kafka.consumer.group-id}'}", concurrency = "10")
    public void consumeChatMessage(Message message) {
        log.info("Received message from Kafka: {}", message);
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
        messagingTemplate.convertAndSend("/subscribe/chat/" + message.getChatRoomId(), chatResponseDTO);

        System.out.println("WebSocket을 통해 특정 채팅방으로 메시지 전달: " + chatResponseDTO);
        } catch (Exception e) {
            System.err.println("Error in KafkaListener: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
