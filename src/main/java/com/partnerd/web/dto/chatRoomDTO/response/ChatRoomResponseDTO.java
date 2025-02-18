package com.partnerd.web.dto.chatRoomDTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ChatRoomResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrivateChatRoomPreviewDTO {
        private Long chatRoomId;
        private String lastMessage;
        private String lastMessageTime;
        private String myNickname;
        private String myProfileImgKeyname;
        private String receiverNickname;
        private String receiverProfileImgKeyname;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollabChatRoomPreviewListDTO {
        private List<CollabChatRoomPreviewDTO> collabChatRooms; // 채팅방 목록
        private Long nextCursor; // 다음 페이지 요청 시 사용할 커서 (null이면 마지막 페이지)
        private boolean hasNext; // 다음 페이지가 있는지 여부

    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrivateChatRoomPreviewListDTO {
        private List<PrivateChatRoomPreviewDTO> privateChatRooms; // 채팅방 목록
        private Long nextCursor; // 다음 페이지 요청 시 사용할 커서 (null이면 마지막 페이지)
        private boolean hasNext; // 다음 페이지가 있는지 여부

    }
}
