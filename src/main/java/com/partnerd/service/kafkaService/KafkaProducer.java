package com.partnerd.service.kafkaService;

import com.partnerd.web.controller.redis.RedisPublisher;
import com.partnerd.web.dto.chatDTO.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class KafkaProducer {


    private final KafkaTemplate<String, Message> kafkaTemplate;


    public void sendMessage(ChatDTO.ChatRequestDTO chatDTO) {

        // 메시지 객체 생성
       Message message = Message.builder()
                .id(UUID.randomUUID().toString())  // 메시지 ID 자동 생성
                .chatRoomId(chatDTO.getChatRoomId())
                .contentType("text")  // 기본 텍스트 타입
                .content(chatDTO.getContent())
                .senderNickname(chatDTO.getSenderNickname())
                .readCount(0) // 초기 읽음 카운트 0
                .build();

       /**
        *  roomId를 Key로 설정하면 Kafka가 자동으로 동일한 roomId 메시지를 같은 파티션으로 보냄
        *
        * ✔ 파티셔닝 전략
        * Kafka 기본 파티셔너(DefaultPartitioner)가 key.hash() % partition 개수 로 파티션을 배정함
        * */

        System.out.println("🟢 Kafka로 메시지 전송 시도: " + message);

        try {
            // 동기 방식으로 메시지 전송 (get() 사용)
            SendResult<String, Message> result = kafkaTemplate.send("chat-topic", String.valueOf(chatDTO.getChatRoomId()), message)
                    .get(5, TimeUnit.SECONDS); // 최대 5초 대기 (Timeout 설정)

            // 메시지 전송 성공 시 로그 출력
            RecordMetadata metadata = result.getRecordMetadata();
            System.out.println("Kafka 메시지 전송 완료: " + message);
            System.out.println("   └ Partition: " + metadata.partition() + ", Offset: " + metadata.offset());

        } catch (Exception e) {
            System.err.println(" Kafka 메시지 전송 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
