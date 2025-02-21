package com.partnerd.config.kafka;

import com.partnerd.service.kafkaService.Message;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVER;

    @Value("${spring.kafka.consumer.group-id}")
    private String GROUP_ID;


    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        JsonDeserializer<Message> deserializer = new JsonDeserializer<>(Message.class);
        deserializer.setRemoveTypeHeaders(false); // ✅ Kafka 헤더에서 타입 정보 제거 안 함
        deserializer.addTrustedPackages("*"); // ✅ 모든 패키지 허용
        deserializer.setUseTypeMapperForKey(false); // ✅ Key는 타입 매핑 필요 없음

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // ✅ JSON 역직렬화 추가

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        // ✅ 역직렬화 오류가 발생해도 Consumer가 계속 실행되도록 설정
        kafkaListenerContainerFactory.setCommonErrorHandler(new DefaultErrorHandler((record, exception) -> {
            System.err.println("Skipping record due to deserialization error: " + record);
        }));
        return kafkaListenerContainerFactory;
    }

}
