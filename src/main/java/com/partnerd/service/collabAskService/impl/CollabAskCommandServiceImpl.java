package com.partnerd.service.collabAskService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CollabAskHandler;
import com.partnerd.apiPaylaod.exception.handler.CollabPostHandler;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.domain.CollabPost;
import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.domain.mapping.CollabAsk;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepository;
import com.partnerd.repository.collabAskRepository.CollabAskRepository;
import com.partnerd.repository.collabPostRepository.CollabPostRepository;
import com.partnerd.service.collabAskService.CollabAskCommandService;
import com.partnerd.service.notifyService.event.CollabAskEvent;
import com.partnerd.web.dto.collabDTO.request.CollabAskRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CollabAskCommandServiceImpl implements CollabAskCommandService {

    private final CollabPostRepository collabPostRepository;
    private final CollabAskRepository collabAskRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ApplicationEventPublisher eventPublisher; // 이벤트 발행 객체


    @Override
    @Transactional
    public CollabAsk addCollabAsk(CollabAskRequestDTO.addCollabAskRquestDTO requestDTO) {

        CollabAsk isCheckDuplication = collabAskRepository.findBySender_Member_idAndCollabPost_id(requestDTO.getSenderId(), requestDTO.getCollabPostId());
        if(isCheckDuplication != null) {
            throw new CollabAskHandler(ErrorStatus.COLLAB_ASK_ALREADY_EXIST);
        }

        CollabPost collabPost = collabPostRepository.findByIdWithMember(requestDTO.getCollabPostId()).orElseThrow(() -> (
            new CollabPostHandler(ErrorStatus.COLLAB_POST_NOT_FOUND)));

        // 보내는 사람 (동아리까지 함께 조회)
        ClubMember sender = clubMemberRepository.findByMemberIdWithClub(requestDTO.getSenderId());

        if (sender == null) {
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        ClubMember receiver = collabPost.getClubMember();

        CollabAsk collabAsk = CollabAsk.builder()
                .collabPost(collabPost)
                .sender(sender)
                .receiver(receiver)
                .request_date(new Date())
                .build();

        collabAsk.setSender(sender);
        collabAsk.setReceiver(receiver);
        CollabAsk newCollabAsk = collabAskRepository.save(collabAsk);

        CollabAskEvent event = CollabAskEvent.builder()
                .receiverId(requestDTO.getSenderId())
                .clubName(sender.getClub().getName())
                .CollabPostTitle(collabPost.getTitle())
                .build();

        eventPublisher.publishEvent(event);

        return newCollabAsk;
    }

    @Override
    public void deleteCollabAsk(Long collabAskId, Long memberId) {

        CollabAsk collabAsk = collabAskRepository.findByIdAndSenderMemberId(collabAskId, memberId).orElseThrow(()->
                new CollabAskHandler(ErrorStatus.COLLAB_ASK_NOT_FOUND));

        collabAskRepository.delete(collabAsk);

    }


}
