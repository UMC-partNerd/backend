package com.partnerd.service.chatService.impl;

import com.partnerd.converter.ChatConverter;
import com.partnerd.domain.chat.ChatMessage;
import com.partnerd.repository.chatRoomRepository.ChatMessageRepository;
import com.partnerd.service.chatService.ChatQueryService;
import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
                .map(ChatConverter::toChatResponseDTO).collect(Collectors.toList());

        return chatDTOList;
    }
}
