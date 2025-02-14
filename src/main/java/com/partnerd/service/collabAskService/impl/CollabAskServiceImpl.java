package com.partnerd.service.collabAskService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CollabAskHandler;
import com.partnerd.converter.collabAskConverter.CollabAskConverter;
import com.partnerd.domain.ChatRoom;
import com.partnerd.domain.mapping.CollabAsk;
import com.partnerd.service.chatRoomService.impl.ChatRoomCommandServiceImpl;
import com.partnerd.service.collabAskService.CollabAskCommandService;
import com.partnerd.service.collabAskService.CollabAskService;
import com.partnerd.web.dto.collabDTO.response.CollabAskResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollabAskServiceImpl implements CollabAskService {

    private final CollabAskCommandService collabAskCommandService;
    private final ChatRoomCommandServiceImpl chatRoomCommandService;

    @Override
    @Transactional
    public CollabAskResponseDTO.addCollabAskResponseDTO createCollabAskAndChatRoom(Long collabPostId, Long memberId) {

        try {
            CollabAsk collabAsk = collabAskCommandService.addCollabAsk(collabPostId, memberId);
            ChatRoom chatRoom = chatRoomCommandService.createCollabChatRoom(collabAsk, memberId);

            return CollabAskConverter.toAddCollabAskResponseDTO(collabAsk, chatRoom);
        } catch (Exception e) {
            throw new CollabAskHandler(ErrorStatus.COLLAB_ASK_TRANSACTION_ROLLBACK);
        }
    }
}
