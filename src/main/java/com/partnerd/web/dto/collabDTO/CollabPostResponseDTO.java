package com.partnerd.web.dto.collabDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

public class CollabPostResponseDTO {

    @Getter
    @Setter
    @Builder
    public static class addCollabPostResultDTO {
        private Long collabPostId;
        LocalDateTime createdAt;
    }


}
