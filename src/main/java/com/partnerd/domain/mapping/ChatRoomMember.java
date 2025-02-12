package com.partnerd.domain.mapping;

import com.partnerd.domain.ChatRoom;
import com.partnerd.domain.Member;
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
        if (chatRoom != null) {
            this.chatRoom.getChatRoomMembers().remove(this);
        }
        this.chatRoom = chatRoom;
        chatRoom.getChatRoomMembers().add(this);
    }



}
