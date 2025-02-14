package com.partnerd.service.kafkaService;

import com.partnerd.domain.chat.ChatMessage;
import com.partnerd.repository.chatRoomRepository.ChatMessageRepository;
import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;

    @KafkaListener(topics = "chat-topic", groupId = "chat-group-id",
            properties = {"spring.json.trusted.packages=com.partnerd.service.kafkaService"})
    public void consumeChatMessage(Message message) {

        System.out.println("Received message from Kafka: " + message);

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(message.getChatRoomId())
                .contentType(message.getContentType())
                .content(message.getContent())
                .senderNickname(message.getSenderNickname())
                .sendDateTime(message.getSendDateTime().toInstant(ZoneOffset.UTC)) // ISODate 형식으로 저장됨
                .readCount(message.getReadCount())
                .build();


        // ✅ 날짜 & 시간 포맷 변환
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String sendDate = message.getSendDateTime().format(dateFormatter);
        String sendTime = message.getSendDateTime().format(timeFormatter);

        // MongoDB 에 저장
        chatMessageRepository.save(chatMessage);

        ChatDTO.ChatResponseDTO chatResponseDTO =ChatDTO.ChatResponseDTO.builder()
                .chatRoomId(message.getChatRoomId())
                .senderNickname(message.getSenderNickname())
                .content(message.getContent())
                .contentType(message.getContentType())
                .sendTime(sendTime)
                .sendDate(sendDate)
                .build();

        // ✅ WebSocket을 통해 특정 채팅방으로 메시지 전달
        messagingTemplate.convertAndSend("/subscribe/chat/" + message.getChatRoomId(), chatResponseDTO);
    }
}
