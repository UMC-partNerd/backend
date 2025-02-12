package com.partnerd.service.chatRoomService;

import com.partnerd.web.dto.chatRoomDTO.response.ChatRoomResponseDTO;

public interface ChatRoomQueryService {

    ChatRoomResponseDTO.CollabChatRoomPreviewListDTO getCollabChatRoomList(Long memberId, Long cursor, int size);
    ChatRoomResponseDTO.PrivateChatRoomPreviewListDTO getPrivateChatRoomList(Long memberId, Long cursor, int size);
}
