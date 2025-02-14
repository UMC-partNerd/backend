package com.partnerd.service.chatService.impl;

import com.partnerd.domain.chat.ChatMessage;
import com.partnerd.repository.chatRoomRepository.ChatMessageRepository;
import com.partnerd.service.chatService.ChatQueryService;
import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatQueryServiceImpl implements ChatQueryService {

    private final ChatMessageRepository chatMessageRepository;
    @Override
    public List<ChatDTO.ChatResponseDTO> getChatList(Long chatRoomId) {

        List<ChatMessage> chatMessageList = chatMessageRepository.findChatMessagesByChatRoomIdOrderBySendDateTimeAsc(chatRoomId);

        List<ChatDTO.ChatResponseDTO> chatDTOList = chatMessageList.stream()
                .map(chatMessage -> {
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(chatMessage.getSendDateTime(), ZoneId.of("Asia/Seoul"));
                    // ✅ 날짜 & 시간 포맷 변환
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                    String sendDate = localDateTime.format(dateFormatter);
                    String sendTime = localDateTime.format(timeFormatter);

                    ChatDTO.ChatResponseDTO chatResponseDTO = ChatDTO.ChatResponseDTO.builder()
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
