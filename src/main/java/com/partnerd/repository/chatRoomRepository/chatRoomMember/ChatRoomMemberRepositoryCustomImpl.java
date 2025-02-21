package com.partnerd.repository.chatRoomRepository.chatRoomMember;

import com.partnerd.domain.*;
import com.partnerd.domain.enums.ChatRoomType;
import com.partnerd.domain.enums.ImageType;
import com.partnerd.domain.mapping.QChatRoomMember;
import com.partnerd.domain.mapping.QCollabAsk;
import com.partnerd.web.dto.chatRoomDTO.response.ChatRoomResponseDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberRepositoryCustomImpl implements ChatRoomMemberRepositoryCustom{

    private final JPAQueryFactory jpaQuery;
    private final QChatRoom qChatRoom = QChatRoom.chatRoom;
    private final QCollabAsk qCollabAsk = QCollabAsk.collabAsk;
    private final QCollabPost qCollabPost = QCollabPost.collabPost;
    private final QClub qClub = QClub.club;
    private final QClubImage qClubImage = QClubImage.clubImage;
    private final QChatRoomMember qChatRoomMember = QChatRoomMember.chatRoomMember;



    @Override
    public List<ChatRoomResponseDTO.CollabChatRoomPreviewDTO> findByMemberIdAndCollabTypeCursorPaging(Long memberId, ChatRoomType type, Long cursorId, Pageable pageable) {

        JPAQuery<ChatRoomResponseDTO.CollabChatRoomPreviewDTO> query = jpaQuery.select(
                Projections.constructor(ChatRoomResponseDTO.CollabChatRoomPreviewDTO.class,
                        qChatRoom.id,
                        qCollabPost.id,
                        qCollabPost.title,
                        qClub.name,
                        qChatRoom.lastMessage,
                        qChatRoom.lastMessageTime,
                        qClubImage.keyName
                        ))
                .from(qChatRoomMember)
                .leftJoin(qChatRoomMember.chatRoom, qChatRoom)
                .leftJoin(qChatRoom.collabAsk, qCollabAsk)
                .leftJoin(qCollabAsk.collabPost, qCollabPost)
                .leftJoin(qCollabPost.clubMember.club, qClub)
                .leftJoin(qClub.clubImgList, qClubImage)
                    .on(qClubImage.image_type.eq(ImageType.valueOf("MAIN")))
                .where(qChatRoomMember.member.id.eq(memberId)
                        .and(qChatRoomMember.chatRoom.chatRoomType.eq(type)));

        // 커서 페이징 적용
        if (cursorId != null) {
            query.where(qChatRoom.id.lt(cursorId)); // ID가 cursorId보다 작은 데이터만 가져옴
        }


        // 최신순 정렬 + 페이징 적용
        return query
                .orderBy(qChatRoom.id.desc()) // 최신순 정렬
                .limit(pageable.getPageSize()) // 지정된 개수만큼 가져오기
                .fetch();
    }

    @Override
    public List<ChatRoomResponseDTO.PrivateChatRoomPreviewDTO> findByMemberIdAndPrivateTypeCursorPaging(Long memberId, ChatRoomType type, Long cursorId, Pageable pageable) {

        QChatRoomMember qMyself = new QChatRoomMember("qMyself");
        QChatRoomMember qReceiver = new QChatRoomMember("qOpponent");


        JPAQuery<ChatRoomResponseDTO.PrivateChatRoomPreviewDTO> query = jpaQuery.select(
                        Projections.constructor(ChatRoomResponseDTO.PrivateChatRoomPreviewDTO.class,
                                qChatRoom.id,
                                qChatRoom.lastMessage,
                                qChatRoom.lastMessageTime,
                                qMyself.member.nickname,
                                qMyself.member.profile_url,
                                qReceiver.member.nickname,
                                qReceiver.member.profile_url
                        ))
                .from(qMyself)
                .leftJoin(qMyself.chatRoom, qChatRoom)
                .leftJoin(qReceiver).on(qReceiver.chatRoom.eq(qChatRoom)
                        .and(qReceiver.member.id.ne(memberId))) // 상대방 회원정보
                .where(qMyself.member.id.eq(memberId)
                        .and(qChatRoom.chatRoomType.eq(type)));


        // 커서 페이징 적용
        if (cursorId != null) {
            query.where(qChatRoom.id.lt(cursorId)); // ID가 cursorId보다 작은 데이터만 가져옴
        }


        // 최신순 정렬 + 페이징 적용
        return query
                .orderBy(qChatRoom.id.desc()) // 최신순 정렬
                .limit(pageable.getPageSize()) // 지정된 개수만큼 가져오기
                .fetch();

    }

    @Override
    public Optional<ChatRoom> findBySenderAndReceiver(Long senderId, Long receiverId) {
        QChatRoomMember sender = new QChatRoomMember("sender");
        QChatRoomMember receiver = new QChatRoomMember("receiver");

        JPAQuery<ChatRoom> query = jpaQuery.select(qChatRoom)
                        .from(sender)
                .join(sender.chatRoom, qChatRoom)
                .join(receiver).on(receiver.chatRoom.eq(qChatRoom))
                .where(sender.member.id.eq(senderId)
                        .and(receiver.member.id.eq(receiverId)));

        return Optional.ofNullable(query.fetchOne());

    }

}
