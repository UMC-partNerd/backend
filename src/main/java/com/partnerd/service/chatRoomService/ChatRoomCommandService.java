package com.partnerd.service.chatRoomService;

import com.partnerd.domain.ChatRoom;

public interface ChatRoomCommandService {



    ChatRoom createCollabChatRoom(Long collabAskId, Long memberId);
    ChatRoom createContactChatRoom(Long memberId, String contactMemberNickname);
}
