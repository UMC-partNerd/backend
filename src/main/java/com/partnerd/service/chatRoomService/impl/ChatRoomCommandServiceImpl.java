package com.partnerd.service.chatRoomService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ChatRoomHandler;
import com.partnerd.apiPaylaod.exception.handler.CollabAskHandler;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.domain.ChatRoom;
import com.partnerd.domain.Member;
import com.partnerd.domain.enums.ChatRoomStatus;
import com.partnerd.domain.enums.ChatRoomType;
import com.partnerd.domain.mapping.ChatRoomMember;
import com.partnerd.domain.mapping.CollabAsk;
import com.partnerd.repository.chatRoomRepository.chatRoom.ChatRoomRepository;
import com.partnerd.repository.chatRoomRepository.chatRoomMember.ChatRoomMemberRepository;
import com.partnerd.repository.collabAskRepository.CollabAskRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.service.chatRoomService.ChatRoomCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;

@Service
@RequiredArgsConstructor
public class ChatRoomCommandServiceImpl implements ChatRoomCommandService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final CollabAskRepository collabAskRepository;
    private final MemberRepository memberRepository;

    @Override
    public ChatRoom createCollabChatRoom(Long collabAskId, Long memberId) {

        // 해당 콜리보 요청 조회
       CollabAsk collabAsk = collabAskRepository.findByIdWithSenderAndReceiver(collabAskId).orElseThrow(() ->{
            throw new CollabAskHandler(ErrorStatus.COLLAB_ASK_NOT_FOUND);
        });

        // 콜라보 요청에 해당하는 채팅방 여부 확인
        if (!chatRoomRepository.findByCollabAsk(collabAsk).isEmpty()) {
            throw new CollabAskHandler(ErrorStatus.CHAT_ROOM_ALREADY_EXIST);
        }

        // 콜라보 요청자가 현재 사용자라면 채팅방 생성 가능
        if (collabAsk.getSender().getMember().getId() != memberId) {
            throw new CollabAskHandler(ErrorStatus.CHAT_ROOM_NOT_AUTHORIZED);
        }

       ChatRoom chatRoom = ChatRoom.builder()
                        .collabAsk(collabAsk)
                        .chatRoomType(ChatRoomType.COLLABORATION)
                        .status(ChatRoomStatus.OPEN)
                        .chatRoomMembers(new LinkedHashSet<>())
                        .build();

        ChatRoomMember senderChatRoomMember = ChatRoomMember.builder()
                .member(collabAsk.getSender().getMember())
                .build();

        senderChatRoomMember.setChatRoom(chatRoom);

        ChatRoomMember receiverChatRoomMember = ChatRoomMember.builder()
                .member(collabAsk.getReceiver().getMember())
                .build();

        receiverChatRoomMember.setChatRoom(chatRoom);

        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom createContactChatRoom(Long memberId, String contactMemberNickname) {

        Member contactReceiveMember  = memberRepository.findByNickname(contactMemberNickname).orElseThrow(() ->
                new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Member contactSenderMember = memberRepository.findById(memberId).orElseThrow(() ->
                new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 중복 채팅방 생성 불가
        if (!chatRoomMemberRepository.findBySenderAndReceiver(contactSenderMember.getId(), contactReceiveMember.getId()).isEmpty()) {
            throw new ChatRoomHandler(ErrorStatus.CHAT_ROOM_ALREADY_EXIST);
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomType(ChatRoomType.PRIVATE)
                .status(ChatRoomStatus.OPEN)
                .chatRoomMembers(new LinkedHashSet<>())
                .build();

        ChatRoomMember receiverMember = ChatRoomMember.builder()
                .member(contactReceiveMember)
                .build();

        receiverMember.setChatRoom(chatRoom);

        ChatRoomMember senderMember = ChatRoomMember.builder()
                .member(contactSenderMember)
                .build();

        senderMember.setChatRoom(chatRoom);

        return chatRoomRepository.save(chatRoom);
    }


}
