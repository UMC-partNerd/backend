package com.partnerd.config;

import com.partnerd.domain.Notification;
import com.partnerd.web.controller.redis.RedisSubscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}") // ✅ 변경
    private String host;

    @Value("${spring.data.redis.port}") // ✅ 변경
    private int port;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        // Redis 연결을 위한 LettuceConnectionFactory 생성
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(configuration);
    }
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisSubscriber redisSubscriber) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactory());
        container.addMessageListener(redisSubscriber, new ChannelTopic("chat-room:*"));
        return container;
    }
    @Bean
    @Primary
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        return redisTemplate;
    }

    @Bean(name = "notificationRedisTemplate")
    public RedisTemplate<String, List<Notification>> redisTemplateForNotification() {
        RedisTemplate<String, List<Notification>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        GenericJackson2JsonRedisSerializer listSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(listSerializer);
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        return redisTemplate;
    }
}
