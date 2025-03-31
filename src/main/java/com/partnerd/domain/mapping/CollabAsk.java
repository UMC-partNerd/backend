package com.partnerd.domain.mapping;

import com.partnerd.domain.ChatRoom;
import com.partnerd.domain.CollabPost;
import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "collab_ask")
public class CollabAsk extends BaseEntity {

    // 콜라보 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 요청 보낸 날짜
    @Column(nullable = false)
    private Date request_date;

    // 보낸 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private ClubMember sender;

    // 받는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private ClubMember receiver;

    // 요청한 콜라보 글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collab_post_id")
    private CollabPost collabPost;

    // 연결된 채팅방
    @OneToOne(mappedBy = "collabAsk", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ChatRoom chatRoom;


    public void setSender(ClubMember sender) {
        if (this.sender != null) {
            this.sender.getSendCollabAsks().remove(this);
        }

        this.sender = sender;
        sender.getSendCollabAsks().add(this);
    }

    public void setReceiver(ClubMember receiver) {
        if(this.receiver != null) {
            this.receiver.getReceivedCollabAsks().remove(this);
        }
        this.receiver = receiver;
        receiver.getReceivedCollabAsks().add(this);
    }

    // ChatRoom을 설정하는 메서드 (양방향 관계 유지)
    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

}
