package com.partnerd.web.dto.chatDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatDTO {

    @NotNull
    private Long chatRoomId;

    @NotNull
    private String contentType;

    @NotNull
    private String content;

    private String senderNickName;

    private Long senderId;

    @NotNull
    private Long collabAskId;
}
