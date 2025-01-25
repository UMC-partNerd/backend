package com.partnerd.web.dto.collabDTO.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CollabAskResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollabAskPreviewListDTO {
        List<CollabAskResponseDTO.CollabAskPreviewDTO> collabAskPreviewDTOLList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollabAskPreviewDTO {
        private Long collabAskId;
        private Long collabPostId;
        private String collabPostTitle;
        private String clubName;
    }



    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addCollabAskResponseDTO {
        private Long collabAskId;
        private Long senderId;
        private Long receiverId;
        private String colloabPostTitle;
    }

}
