package com.partnerd.service.collabPostService;

import com.partnerd.domain.CollabInquiry;
import com.partnerd.web.dto.collabDTO.request.CollabInquiryRequestDTO;

public interface CollabInquiryCommandService {

    CollabInquiry addCollabInquiry(CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO);
    CollabInquiry addChildInquiry(Long parentId, CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO);

    CollabInquiry modifyCollabInquiry(Long collabInquiryId, String contents);
    void deleteCollabInquiry(Long collabInquiryId);
    void deleteCollabChildInquiry(Long collabInquiryId);
}
