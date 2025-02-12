package com.partnerd.service.chatRoomService;

import com.partnerd.domain.ChatRoom;
import com.partnerd.domain.mapping.ChatRoomMember;
import com.partnerd.web.dto.chatRoomDTO.response.ChatRoomResponseDTO;

import java.util.List;

public interface ChatRoomQueryService {

    ChatRoomResponseDTO.CollabChatRoomPreviewListDTO getCollabChatRoomList(Long memberId, Long cursor, int size);
    ChatRoomResponseDTO.PrivateChatRoomPreviewListDTO getPrivateChatRoomList(Long memberId, Long cursor, int size);
}
