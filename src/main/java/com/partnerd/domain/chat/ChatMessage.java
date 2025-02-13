package com.partnerd.domain.chat;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;


// Mongo DB 저장
@Getter
@ToString
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Document(collection = "chatMessages")  // ✅ MongoDB 컬렉션 이름
public class ChatMessage {

    @Id
    private String id;
    private Long chatRoomId;
    private String senderNickname;
    private String contentType;
    private String content;
    private LocalDateTime sendDateTime;
    private Integer readCount;
}
