package com.partnerd.service.collabAskService;

import com.partnerd.web.dto.collabDTO.response.CollabAskResponseDTO;

public interface CollabAskService {
    CollabAskResponseDTO.addCollabAskResponseDTO createCollabAskAndChatRoom(Long collabPostId, Long memberId);
}
