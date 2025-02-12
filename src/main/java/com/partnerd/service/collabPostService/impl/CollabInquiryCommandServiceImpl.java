package com.partnerd.service.collabPostService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CollabInquiryHandler;
import com.partnerd.apiPaylaod.exception.handler.CollabPostHandler;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.converter.collabPostConverter.CollabInquiryConverter;
import com.partnerd.domain.CollabInquiry;
import com.partnerd.repository.collabPostRepository.collabInquiry.CollabInquiryRepository;
import com.partnerd.repository.collabPostRepository.collabPost.CollabPostRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.service.collabPostService.CollabInquiryCommandService;
import com.partnerd.web.dto.collabDTO.request.CollabInquiryRequestDTO;
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
    public CollabInquiry addCollabInquiry(CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO, Long memberId) {

        CollabInquiry collabInquiry = CollabInquiryConverter.toCollabInquiry(requestDTO);

        collabInquiry.setCollabPost(collabPostRepository.findByIdWithInquiry(requestDTO.getCollabPostId()).orElseThrow(() ->
                new CollabPostHandler(ErrorStatus.COLLAB_POST_NOT_FOUND)));

        collabInquiry.setMember(memberRepository.findById(memberId).orElseThrow(() ->
                new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND)));

        return collabInquiryRepository.save(collabInquiry);

    }

    @Override
    @Transactional
    public CollabInquiry addChildInquiry(Long parentId, CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO, Long memberId) {

        CollabInquiry collabInquiry = CollabInquiryConverter.toCollabInquiry(requestDTO);

        if (parentId != null) {
            // 부모댓글 양방향 매핑
            CollabInquiry parentInquiry = collabInquiryRepository.findByIdWithMemberAndPost(parentId).orElseThrow(() ->
                    new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND));

            collabInquiry.addParentInquiry(parentInquiry);

            collabInquiry.setMember(memberRepository.findById(memberId).orElseThrow(() ->
                    new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND)));
            collabInquiry.setCollabPost(parentInquiry.getCollabPost());

        } else {
            throw new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND);
        }

        return collabInquiryRepository.save(collabInquiry);
    }

    @Override
    @Transactional
    public CollabInquiry modifyCollabInquiry(Long collabInquiryId, String contents, Long memberId) {

        CollabInquiry collabInquiry = collabInquiryRepository.findByIdWithMember(collabInquiryId).orElseThrow(() ->
                new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND));

        collabInquiry.validateAuthor(memberId);
        collabInquiry.updateCollabInquiry(contents);

        return collabInquiryRepository.save(collabInquiry);
    }

    @Override
    @Transactional
    public void deleteCollabInquiry(Long collabInquiryId, Long memberId) {

        CollabInquiry collabInquiry = collabInquiryRepository.findByIdWithParentIdIsNULL(collabInquiryId).orElseThrow(() ->
                new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND));

        collabInquiry.validateAuthor(memberId);

        if (collabInquiry.getChildren().size() != 0) {
            collabInquiry.changeIsDeleted();
        } else {
            collabInquiryRepository.delete(collabInquiry);
        }

    }

    @Override
    @Transactional
    public void deleteCollabChildInquiry(Long collabInquiryId, Long memberId) {

        CollabInquiry collabInquiry = collabInquiryRepository.findByIdWithParentIdIsNOTNULL(collabInquiryId).orElseThrow(() ->
                new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND));

        CollabInquiry parentInquiry = collabInquiry.getParentInquiry();

        collabInquiry.validateAuthor(memberId);

        if (parentInquiry.getIsDeleted() == 1 & parentInquiry.getChildren().size() == 1) {
            collabInquiryRepository.delete(collabInquiry.getParentInquiry());
        } else {
            parentInquiry.getChildren().remove(collabInquiry);
            collabInquiryRepository.delete(collabInquiry);
        }

    }

    @Override
    @Transactional
    public Integer addLike (Long collabInquiryId, Long memberId) {

        CollabInquiry collabInquiry = collabInquiryRepository.findByIdWithMember(collabInquiryId).orElseThrow(() ->
                new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND));

        collabInquiry.validateAuthor(memberId);
        collabInquiry.addLikes();

        return collabInquiry.getLikes();
    }

    @Override
    @Transactional
    public Integer removeLike(Long collabInquiryId, Long memberId) {

        CollabInquiry collabInquiry = collabInquiryRepository.findByIdWithMember(collabInquiryId).orElseThrow(() ->
                new CollabInquiryHandler(ErrorStatus.COLLAB_INQUIRY_ID_NOT_FOUND));

        collabInquiry.validateAuthor(memberId);

        // 좋아요 개수가 0이면 취소할 수 없도록 예외 발생
        if (collabInquiry.getLikes() <= 0) {
            throw new CollabInquiryHandler(ErrorStatus.CANNOT_REMOVE_LIKE_BELOW_ZERO);
        }

        collabInquiry.removeLikes();

        return collabInquiry.getLikes();
    }


}
