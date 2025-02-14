package com.partnerd.service.kafkaService;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
    @NotNull
    private String senderNickname; // 보내는 사람의 닉네임이 사용자의 닉네임곽 같으면 클라이언트에서 사용자 자신이 보낸 메세지로 판단.
    private LocalDateTime sendDateTime; // ISODate으로 저장해야지 MongoDB 에서 정렬 가능
    private Integer readCount;

}
