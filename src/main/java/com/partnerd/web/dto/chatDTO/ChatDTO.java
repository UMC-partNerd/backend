package com.partnerd.web.dto.chatDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public class ChatDTO {


    @Getter
    @Builder
    public static class ChatRequestDTO {

        @NotNull
        private Long chatRoomId;

        @NotNull
        private String contentType;

        @NotNull
        private String content;

        private String senderNickname;
    }

    @Getter
    @Builder
    public static class ChatResponseDTO {

        @NotNull
        private Long chatRoomId;

        @NotNull
        private String contentType;

        @NotNull
        private String content;

        private String senderNickname;
        private String sendTime;
        private String sendDate;
    }



}
