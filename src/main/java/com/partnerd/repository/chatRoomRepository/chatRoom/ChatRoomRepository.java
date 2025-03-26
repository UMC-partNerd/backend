package com.partnerd.repository.chatRoomRepository.chatRoom;

import com.partnerd.mongoRepository.domain.ChatRoom;
import com.partnerd.mongoRepository.domain.mapping.CollabAsk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {
    Optional<ChatRoom> findByCollabAsk(CollabAsk collabAsk);
}
