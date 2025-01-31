package com.partnerd.domain.chat;

import lombok.*;

import java.time.LocalDateTime;


// Mongo DB 저장
@Getter
@ToString
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Chatting {

    private String id;
    private Integer chatRoomId;
    private Integer senderId;
    private String senderName;
    private String contentType;
    private String content;
    private LocalDateTime sendDate;
    private long readCount;
}
