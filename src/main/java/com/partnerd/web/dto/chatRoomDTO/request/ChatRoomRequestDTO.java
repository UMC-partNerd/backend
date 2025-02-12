package com.partnerd.web.dto.chatRoomDTO.request;

import com.partnerd.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ChatRoomRequestDTO {

    @Builder
    @Getter
    public static class addChatRoomDTO {
        private Long chatRoomId;
        private String collabPostTitle;
        private List<Member> chatRoomMembers;
    }



}
