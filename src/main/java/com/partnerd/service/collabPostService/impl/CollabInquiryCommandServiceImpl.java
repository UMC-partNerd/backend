package com.partnerd.service.collabPostService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CollabInquiryHandler;
import com.partnerd.apiPaylaod.exception.handler.CollabPostHandler;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.converter.collabPostConverter.CollabInquiryConverter;
import com.partnerd.domain.CollabInquiry;
import com.partnerd.domain.Member;
import com.partnerd.repository.collabPostRepository.CollabInquiryRepository;
import com.partnerd.repository.collabPostRepository.CollabPostRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.service.collabPostService.CollabInquiryCommandService;
import com.partnerd.web.dto.collabDTO.request.CollabInquiryRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollabInquiryCommandServiceImpl implements CollabInquiryCommandService {

    private final CollabInquiryRepository collabInquiryRepository;
    private final CollabPostRepository collabPostRepository;
    private final MemberRepository memberRepository;

    @Override
    public CollabInquiry addCollabInquiry(CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO) {

        CollabInquiry collabInquiry = CollabInquiryConverter.toCollabInquiry(requestDTO);

        collabInquiry.setCollabPost(collabPostRepository.findById(requestDTO.getCollabPostId()).orElseThrow(() ->
                new CollabPostHandler(ErrorStatus.COLLAB_POST_NOT_FOUND)));

        // 멤버 하드 코딩
        collabInquiry.setMember(memberRepository.findById(1L).orElseThrow(() ->
                new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND)));

        return collabInquiryRepository.save(collabInquiry);

    }

    @Override
    public CollabInquiry addChildInquiry(Long parentId, CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO) {

        CollabInquiry collabInquiry = CollabInquiryConverter.toCollabInquiry(requestDTO);

        if (parentId != null) {
            // 부모댓글 양방향 매핑
            CollabInquiry parentInquiry = collabInquiryRepository.findById(parentId).orElseThrow(() ->
                    new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND));
            collabInquiry.addParentInquiry(parentInquiry);

            collabInquiry.setMember(memberRepository.findById(1L).orElseThrow(() ->
                    new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND)));
            collabInquiry.setCollabPost(collabPostRepository.findById(requestDTO.getCollabPostId()).orElseThrow(() ->
                    new CollabPostHandler(ErrorStatus.COLLAB_POST_NOT_FOUND)));
            // 답변완료 상태로 변경
            parentInquiry.updateStatus();
            // collabInquiryRepository.flush();

        } else {
            throw new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND);
        }

        return collabInquiryRepository.save(collabInquiry);
    }




}
