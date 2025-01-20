package com.partnerd.service.collabPostService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CollabInquiryHandler;
import com.partnerd.apiPaylaod.exception.handler.CollabPostHandler;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.converter.collabPostConverter.CollabInquiryConverter;
import com.partnerd.domain.CollabInquiry;
import com.partnerd.repository.collabPostRepository.CollabInquiryRepository;
import com.partnerd.repository.collabPostRepository.CollabPostRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.service.collabPostService.CollabInquiryCommandService;
import com.partnerd.web.dto.collabDTO.request.CollabInquiryRequestDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollabInquiryCommandServiceImpl implements CollabInquiryCommandService {

    private final CollabInquiryRepository collabInquiryRepository;
    private final CollabPostRepository collabPostRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public CollabInquiry addCollabInquiry(CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO) {

        CollabInquiry collabInquiry = CollabInquiryConverter.toCollabInquiry(requestDTO);


        collabInquiry.setCollabPost(collabPostRepository.findByIdWithMember(requestDTO.getCollabPostId()).orElseThrow(() ->
                new CollabPostHandler(ErrorStatus.COLLAB_POST_NOT_FOUND)));

        // 멤버 하드 코딩
        collabInquiry.setMember(memberRepository.findById(1L).orElseThrow(() ->
                new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND)));

        return collabInquiryRepository.save(collabInquiry);

    }

    @Override
    @Transactional
    public CollabInquiry addChildInquiry(Long parentId, CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO) {

        CollabInquiry collabInquiry = CollabInquiryConverter.toCollabInquiry(requestDTO);

        if (parentId != null) {
            // 부모댓글 양방향 매핑
            CollabInquiry parentInquiry = collabInquiryRepository.findByIdWithMemberAndPost(parentId).orElseThrow(() ->
                    new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND));

            collabInquiry.addParentInquiry(parentInquiry);

            collabInquiry.setMember(memberRepository.findById(1L).orElseThrow(() ->
                    new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND)));
            collabInquiry.setCollabPost(parentInquiry.getCollabPost());
            // collabInquiryRepository.flush();

        } else {
            throw new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND);
        }

        return collabInquiryRepository.save(collabInquiry);
    }

    @Override
    @Transactional
    public CollabInquiry modifyCollabInquiry(Long collabInquiryId, String contents) {

        CollabInquiry collabInquiry = collabInquiryRepository.findByIdWithMember(collabInquiryId).orElseThrow(() ->
                new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND));

        collabInquiry.updateCollabInquiry(contents);

        return collabInquiryRepository.save(collabInquiry);
    }

    @Override
    @Transactional
    public void deleteCollabInquiry(Long collabInquiryId) {

        CollabInquiry collabInquiry = collabInquiryRepository.findByIdWithParentIdIsNULL(collabInquiryId).orElseThrow(() ->
                new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND));

        if (collabInquiry.getChildren().size() != 0) {
            collabInquiry.changeIsDeleted();
        } else {
            collabInquiryRepository.delete(collabInquiry);
        }

    }

    @Override
    @Transactional
    public void deleteCollabChildInquiry(Long collabInquiryId) {

        CollabInquiry collabInquiry = collabInquiryRepository.findByIdWithParentIdIsNOTNULL(collabInquiryId).orElseThrow(() ->
                new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND));

        CollabInquiry parentInquiry = collabInquiry.getParentInquiry();

        if (parentInquiry.getIsDeleted() == 1 && collabInquiry.getParentInquiry().getChildren().size() == 1) {
            System.out.println("부모 자식 모두 삭제");
            collabInquiryRepository.delete(collabInquiry.getParentInquiry());
        } else {
            System.out.println("자식만 삭제");
            parentInquiry.getChildren().remove(collabInquiry);
            collabInquiryRepository.delete(collabInquiry);
        }

    }


}
