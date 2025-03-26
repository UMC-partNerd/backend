package com.partnerd.repository.chatRoomRepository.chatRoomMember;

import com.partnerd.domain.ChatRoom;
import com.partnerd.domain.enums.ChatRoomType;
import com.partnerd.web.dto.chatRoomDTO.response.ChatRoomResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface ChatRoomMemberRepositoryCustom {

    List<ChatRoomResponseDTO.CollabChatRoomPreviewDTO> findByMemberIdAndCollabTypeCursorPaging(Long memberId, ChatRoomType type, Long cursorId, Pageable pageable);

    List<ChatRoomResponseDTO.PrivateChatRoomPreviewDTO> findByMemberIdAndPrivateTypeCursorPaging(Long memberId, ChatRoomType type, Long cursorId, Pageable pageable);

    Optional<ChatRoom> findBySenderAndReceiver(Long senderId, Long receiverId);
}
