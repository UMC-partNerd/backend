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
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String AUTO_OFFSET_RESET;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String TRUST_PACKAGE;



    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        // JsonDeserializer 관련 프로퍼티로 설정 (프로퍼티 방식)
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.partnerd.service.kafkaService,java.util,java.lang");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Message.class.getName());
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);  // 헤더에서 타입 정보를 사용하지 않음

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        // ✅ 역직렬화 오류가 발생해도 Consumer가 계속 실행되도록 설정
        kafkaListenerContainerFactory.setCommonErrorHandler(new DefaultErrorHandler((record, exception) -> {
            System.err.println("Skipping record due to deserialization error: " + record);
            exception.printStackTrace();
        }));
        return kafkaListenerContainerFactory;
    }

}
