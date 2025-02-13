package com.partnerd.service.chatService.impl;

import com.partnerd.domain.chat.ChatMessage;
import com.partnerd.repository.chatRoomRepository.ChatMessageRepository;
import com.partnerd.service.chatService.ChatQueryService;
import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatQueryServiceImpl implements ChatQueryService {

    private final ChatMessageRepository chatMessageRepository;
    @Override
    public List<ChatDTO.ChatResponseDTO> getChatList(Long chatRoomId) {

        List<ChatMessage> chatMessageList = chatMessageRepository.findChatMessagesByChatRoomIdOOrderBySendDateTimeAsc(chatRoomId);

        List<ChatDTO.ChatResponseDTO> chatDTOList = chatMessageList.stream()
                .map(chatMessage -> {

                    // ✅ "YYYY-MM-DD" 형식의 날짜 저장
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String sendDate = dateFormat.format(chatMessage.getSendDateTime());

                    // ✅ "HH:mm" 형식의 시간 저장
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    String sendTime = timeFormat.format(chatMessage.getSendDateTime());

                    ChatDTO.ChatResponseDTO chatResponseDTO =ChatDTO.ChatResponseDTO.builder()
                            .chatRoomId(chatMessage.getChatRoomId())
                            .senderNickname(chatMessage.getSenderNickname())
                            .content(chatMessage.getContent())
                            .contentType(chatMessage.getContentType())
                            .sendTime(sendTime)
                            .sendDate(sendDate)
                            .build();
                    return chatResponseDTO;
                }).collect(Collectors.toList());

        return chatDTOList;
    }
}
