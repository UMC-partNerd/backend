package com.partnerd.kafka;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private String id;

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

    private long sendTime;
    private Integer readCount;
}
