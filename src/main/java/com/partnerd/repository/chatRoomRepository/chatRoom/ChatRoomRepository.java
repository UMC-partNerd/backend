package com.partnerd.repository.chatRoomRepository.chatRoom;

import com.partnerd.domain.ChatRoom;
import com.partnerd.domain.mapping.CollabAsk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {
    Optional<ChatRoom> findByCollabAsk(CollabAsk collabAsk);
    Optional<ChatRoom> findBySenderAndReceiver(String senderNickname, String receiverNickname);
}
