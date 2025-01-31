package com.partnerd.web.dto.collabDTO.request;

import lombok.Getter;
import lombok.Setter;

public class CollabInquiryRequestDTO {

    @Getter
    @Setter
    public static class addCollabInquiryDTO {

        private Long collabPostId;
        private String contents;

    }

}
