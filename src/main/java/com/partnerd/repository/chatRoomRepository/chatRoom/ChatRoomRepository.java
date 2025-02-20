package com.partnerd.repository.chatRoomRepository.chatRoom;

import com.partnerd.domain.ChatRoom;
import com.partnerd.domain.mapping.CollabAsk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.collabAsk.id = :id")
    Optional<ChatRoom> findByCollabAskId(@Param("collabAskId") Long collabAskId);
}
