package com.partnerd.domain;


import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.enums.ChatRoomStatus;
import com.partnerd.domain.enums.ChatRoomType;
import com.partnerd.domain.mapping.ChatRoomMember;
import com.partnerd.domain.mapping.CollabAsk;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 채팅방 ID

    @OneToOne
    @JoinColumn(name = "collab_ask_id")
    @Column(nullable = true)
    private CollabAsk collabAsk; // 해당 채팅방과 관련된 콜라보 요청 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatRoomType chatRoomType;

    @Column(nullable = true)
    private LocalDateTime closedAt; // 채팅방 종료 일시 (선택적)

    // 채팅방의 현재 상태 (OPEN, CLOSED 등)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatRoomStatus status;

    @Column(nullable = true)
    private String lastMessage;

    @Column(nullable = true)
    private String lastMessageTime;

    // 중간 테이블을 통해 관리되는 멤버 목록
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatRoomMember> chatRoomMembers = new LinkedHashSet<>(); // 채팅방 참여 사용자 목록


}
