package com.partnerd.converter;

import com.partnerd.mongoRepository.domain.chat.ChatMessage;
import com.partnerd.web.dto.chatDTO.ChatDTO;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ChatConverter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final ZoneId KST_ZONE = ZoneId.of("Asia/Seoul");

    // ✅ UTC -> KST 변환 메서드
    public static ZonedDateTime convertToKST(Instant utcTime) {
        return utcTime.atZone(KST_ZONE);
    }

    // ✅ ChatMessage를 ChatResponseDTO로 변환
    public static ChatDTO.ChatResponseDTO toChatResponseDTO(ChatMessage chatMessage) {
        ZonedDateTime kstTime = convertToKST(chatMessage.getSendDateTime());

        return ChatDTO.ChatResponseDTO.builder()
                .chatRoomId(chatMessage.getChatRoomId())
                .senderNickname(chatMessage.getSenderNickname())
                .content(chatMessage.getContent())
                .contentType(chatMessage.getContentType())
                .sendDate(kstTime.format(DATE_FORMATTER))
                .sendTime(kstTime.format(TIME_FORMATTER))
                .build();
    }
}
