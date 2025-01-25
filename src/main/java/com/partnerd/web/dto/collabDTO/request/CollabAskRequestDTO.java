package com.partnerd.web.dto.collabDTO.request;

import lombok.Builder;
import lombok.Getter;

public class CollabAskRequestDTO {

    @Builder
    @Getter
    public static class addCollabAskRquestDTO {
        private Long collabPostId;
        private Long senderId;
    }



}
