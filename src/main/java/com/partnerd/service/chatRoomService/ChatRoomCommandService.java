package com.partnerd.service.chatRoomService;

import com.partnerd.domain.ChatRoom;
import com.partnerd.domain.mapping.CollabAsk;

public interface ChatRoomCommandService {
    ChatRoom createCollabChatRoom(CollabAsk collabAsk, Long memberId);
    ChatRoom createContactChatRoom(Long memberId, String contactMemberNickname);
}
