package com.partnerd.service.chatRoomService.impl;

import com.partnerd.domain.enums.ChatRoomType;
import com.partnerd.repository.chatRoomRepository.chatRoomMember.ChatRoomMemberRepository;
import com.partnerd.service.chatRoomService.ChatRoomQueryService;
import com.partnerd.web.dto.chatRoomDTO.response.ChatRoomResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomQueryServiceImpl implements ChatRoomQueryService {

    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    public ChatRoomResponseDTO.CollabChatRoomPreviewListDTO getCollabChatRoomList(Long memberId, Long cursor, int size) {

        Pageable pageable = PageRequest.of(0, size);

        List<ChatRoomResponseDTO.CollabChatRoomPreviewDTO> collabchatRooms = chatRoomMemberRepository.findByMemberIdAndCollabTypeCursorPaging(memberId, ChatRoomType.COLLABORATION, cursor, pageable);

        boolean hasNext = collabchatRooms.size() > size;
        Long nextCursor = hasNext ? collabchatRooms.get(size).getChatRoomId() : null;

        return ChatRoomResponseDTO.CollabChatRoomPreviewListDTO.builder()
                .collabChatRooms(collabchatRooms.subList(0, Math.min(collabchatRooms.size(), size)))
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    @Override
    public ChatRoomResponseDTO.PrivateChatRoomPreviewListDTO getPrivateChatRoomList(Long memberId, Long cursor, int size) {

        Pageable pageable = PageRequest.of(0, size);

        List<ChatRoomResponseDTO.PrivateChatRoomPreviewDTO> privateChatRooms = chatRoomMemberRepository.findByMemberIdAndPrivateTypeCursorPaging(memberId, ChatRoomType.PRIVATE, cursor, pageable);

        boolean hasNext = privateChatRooms.size() > size;
        Long nextCursor = hasNext ? privateChatRooms.get(size).getChatRoomId() : null;

        return ChatRoomResponseDTO.PrivateChatRoomPreviewListDTO.builder()
                .privateChatRooms(privateChatRooms)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }
}
