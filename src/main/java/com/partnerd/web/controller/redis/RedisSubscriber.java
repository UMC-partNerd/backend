package com.partnerd.web.controller.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // ✅ Redis에서 메시지 수신
            String receivedMessage = new String(message.getBody());
            System.out.println(receivedMessage);
            ChatDTO.ChatResponseDTO chatResponseDTO = objectMapper.readValue(receivedMessage, ChatDTO.ChatResponseDTO.class);

            log.info("📩 Redis 메시지 수신: {}", chatResponseDTO);
            System.out.println("📩 Redis 메시지 수신: {}" + chatResponseDTO);

            // ✅ WebSocket을 통해 클라이언트로 전송
            messagingTemplate.convertAndSend("/subscribe/chat/" + chatResponseDTO.getChatRoomId(), chatResponseDTO);

            log.info("📤 WebSocket으로 메시지 전송: {}", chatResponseDTO);

        } catch (Exception e) {
            log.error("❌ Redis 메시지 처리 중 오류 발생", e);
        }
    }

}
