package com.partnerd.repository.chatRoomRepository.mongoRepository;

import com.partnerd.domain.chat.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findChatMessagesByChatRoomIdOrderBySendDateTimeAsc(Long chatRoomId);

}
