package com.partnerd.web.dto.collabDTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CollabInquiryResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addCollabInquiryResponseDTO {
        private Long collabInquiryId;
        private String contents;
        private String nickname;
        private Long parentId;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class deleteCollabInquiryResponseDTO {
        private Long id;
        private String contents;
    }

}
