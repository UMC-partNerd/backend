package com.partnerd.domain.chat;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
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
    @Field(targetType = FieldType.DATE_TIME)  // ✅ `ISODate`로 변환
    private Instant sendDateTime;  // ✅ Instant 사용하면 MongoDB에서 자동 변환됨
    private Integer readCount;
}
