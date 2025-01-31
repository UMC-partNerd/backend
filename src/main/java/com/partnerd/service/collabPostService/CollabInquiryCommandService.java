package com.partnerd.service.collabPostService;

import com.partnerd.domain.CollabInquiry;
import com.partnerd.web.dto.collabDTO.request.CollabInquiryRequestDTO;

public interface CollabInquiryCommandService {

    CollabInquiry addCollabInquiry(CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO, Long memberId);
    CollabInquiry addChildInquiry(Long parentId, CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO, Long memberId);

    CollabInquiry modifyCollabInquiry(Long collabInquiryId, String contents, Long memberId);
    void deleteCollabInquiry(Long collabInquiryId, Long memberId);
    void deleteCollabChildInquiry(Long collabInquiryId, Long memberId);
    Integer addLike(Long CollabInquiryId, Long memberId);
    Integer removeLike(Long CollabInquiryId, Long memberId);
}
