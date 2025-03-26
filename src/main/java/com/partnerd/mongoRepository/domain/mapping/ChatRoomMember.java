package com.partnerd.mongoRepository.domain.mapping;

import com.partnerd.mongoRepository.domain.ChatRoom;
import com.partnerd.mongoRepository.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    public void setChatRoom(ChatRoom chatRoom) {
        if (this.chatRoom != null) {
            this.chatRoom.getChatRoomMembers().remove(this);
        }
        this.chatRoom = chatRoom;
        chatRoom.getChatRoomMembers().add(this);
    }



}
