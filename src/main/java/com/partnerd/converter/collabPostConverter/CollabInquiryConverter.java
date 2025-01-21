package com.partnerd.converter.collabPostConverter;

import com.partnerd.domain.CollabInquiry;
import com.partnerd.web.dto.collabDTO.request.CollabInquiryRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabInquiryResponseDTO;

public class CollabInquiryConverter {

    public static CollabInquiry toCollabInquiry (CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO) {

        return CollabInquiry.builder()
                .contents(requestDTO.getContents())
                .isDeleted(0)
                .build();
    }

    public static CollabInquiryResponseDTO.addCollabInquiryResponseDTO toCollabInquiryResultDTO (CollabInquiry collabInquiry) {

        return CollabInquiryResponseDTO.addCollabInquiryResponseDTO.builder()
                .collabInquiryId(collabInquiry.getId())
                .contents(collabInquiry.getContents())
                .nickname(collabInquiry.getMember().getNickname())
                .build();

    }

    public static CollabInquiryResponseDTO.addCollabInquiryResponseDTO toChildCollabInquiryResultDTO (CollabInquiry collabInquiry) {

        return CollabInquiryResponseDTO.addCollabInquiryResponseDTO.builder()
                .collabInquiryId(collabInquiry.getId())
                .contents(collabInquiry.getContents())
                .nickname(collabInquiry.getMember().getNickname())
                .parentId(collabInquiry.getParentInquiry().getId())
                .build();

    }

}
