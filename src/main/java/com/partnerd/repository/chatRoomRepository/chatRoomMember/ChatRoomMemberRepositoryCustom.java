package com.partnerd.repository.chatRoomRepository.chatRoomMember;

import com.partnerd.domain.enums.ChatRoomType;
import com.partnerd.domain.mapping.ChatRoomMember;
import com.partnerd.web.dto.chatRoomDTO.response.ChatRoomResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ChatRoomMemberRepositoryCustom {

    List<ChatRoomResponseDTO.CollabChatRoomPreviewDTO> findByMemberIdAndCollabTypeCursorPaging(Long memberId, ChatRoomType type, Long cursorId, Pageable pageable);

    List<ChatRoomResponseDTO.PrivateChatRoomPreviewDTO> findByMemberIdAndPrivateTypeCursorPaging(Long memberId, ChatRoomType type, Long cursorId, Pageable pageable);

}
