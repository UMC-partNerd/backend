package com.partnerd.web.controller.chatController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.service.chatService.ChatQueryService;
import com.partnerd.service.kafkaService.KafkaProducer;
import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final KafkaProducer kafkaProducer;
    private final ChatQueryService chatQueryService;

    @GetMapping("/api/chat/{chatRoomId}")
    public ApiResponse<List<ChatDTO.ChatResponseDTO>> getChatList (@PathVariable(name = "chatRoomId") Long chatRoomId) {
        return ApiResponse.onSuccess(chatQueryService.getChatList(chatRoomId));
    }


    @MessageMapping("/chat/{chatRoomId}")
    public void sendChatMessage(@Payload ChatDTO.ChatRequestDTO chatDTO) {
        // Kafka로 메시지 전송
        kafkaProducer.sendMessage(chatDTO);
    }

    // 입장 메세지 필요시 구현 예정
    @MessageMapping("/chat/{chatRoomId}/enter")
    public void enterChatRoom (@RequestBody ChatDTO chatDTO,
                               SimpMessageHeaderAccessor headerAccessor) {

    }
}
