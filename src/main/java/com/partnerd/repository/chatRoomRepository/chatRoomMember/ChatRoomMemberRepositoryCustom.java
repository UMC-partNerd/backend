package com.partnerd.repository.chatRoomRepository.chatRoomMember;

import com.partnerd.domain.enums.ChatRoomType;
import com.partnerd.web.dto.chatRoomDTO.response.ChatRoomResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ChatRoomMemberRepositoryCustom {

    List<ChatRoomResponseDTO.CollabChatRoomPreviewDTO> findByMemberIdAndCollabTypeCursorPaging(Long memberId, ChatRoomType type, Long cursorId, Pageable pageable);

    List<ChatRoomResponseDTO.PrivateChatRoomPreviewDTO> findByMemberIdAndPrivateTypeCursorPaging(Long memberId, ChatRoomType type, Long cursorId, Pageable pageable);

}
