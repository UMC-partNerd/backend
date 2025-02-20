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
import com.partnerd.repository.collabPostRepository.collabPost.CollabPostRepository;
import com.partnerd.service.collabAskService.CollabAskCommandService;
import com.partnerd.service.notifyService.event.CollabAskEvent;
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
    public CollabAsk addCollabAsk(Long collabPostId, Long memberId) {

        CollabPost collabPost = collabPostRepository.findByIdWithMember(collabPostId).orElseThrow(() -> (
            new CollabPostHandler(ErrorStatus.COLLAB_POST_NOT_FOUND)));

        // 요청자가 해당 글의 작성자이면 요청 불가
        if (collabPost.getClubMember().getMember().getId() == memberId) {
            throw new CollabAskHandler(ErrorStatus.COLLAB_ASK_NOT_AUTHORIZED);
        }

        // 내가 이미 보낸 요청인지 확인 (중복 요청 방지)
        CollabAsk isCheckDuplication = collabAskRepository.findByClubMemberMemberId(memberId, collabPostId);
        if(isCheckDuplication != null) {
            throw new CollabAskHandler(ErrorStatus.COLLAB_ASK_ALREADY_EXIST);
        }
        
        // 보내는 사람 (동아리까지 함께 조회)
        ClubMember sender = clubMemberRepository.findByMemberIdWithClub(memberId);

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
                .receiverId(memberId)
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
