package com.partnerd.web.dto.chatRoomDTO.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ChatRoomResponseDTO {

    @Builder
    @Getter
    public static class CollabChatRoomPreviewDTO {

        private Long chatRoomId;
        private Long collabPostId;
        private String collabPostTitle;
        private String clubName;
        private String lastMessage;
        private String lastMessageTime;
        private String clubProfileImgKeyname;

    }
    @Builder
    @Getter
    public static class PrivateChatRoomPreviewDTO {
        private Long chatRoomId;
        private String lastMessage;
        private String lastMessageTime;
        private String receiverNickname;
        private String receiverProfileImgKeyname;
        private String myNickname;
        private String myProfileImgkeyname;
    }

    @Builder
    @Getter
    public static class CollabChatRoomPreviewListDTO {
        private List<CollabChatRoomPreviewDTO> collabChatRooms; // 채팅방 목록
        private Long nextCursor; // 다음 페이지 요청 시 사용할 커서 (null이면 마지막 페이지)
        private boolean hasNext; // 다음 페이지가 있는지 여부

    }
    @Builder
    @Getter
    public static class PrivateChatRoomPreviewListDTO {
        private List<PrivateChatRoomPreviewDTO> privateChatRooms; // 채팅방 목록
        private Long nextCursor; // 다음 페이지 요청 시 사용할 커서 (null이면 마지막 페이지)
        private boolean hasNext; // 다음 페이지가 있는지 여부

    }
}
