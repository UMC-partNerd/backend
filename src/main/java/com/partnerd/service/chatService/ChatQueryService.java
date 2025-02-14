package com.partnerd.service.chatService;

import com.partnerd.web.dto.chatDTO.ChatDTO;

import java.util.List;

public interface ChatQueryService {

    List<ChatDTO.ChatResponseDTO> getChatList(Long chatRoomId);

}
