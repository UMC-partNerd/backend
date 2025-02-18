package com.partnerd.domain.mapping;

import com.partnerd.domain.Club;
import com.partnerd.domain.CollabPost;
import com.partnerd.domain.Member;
import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.enums.ActiveType;
import com.partnerd.domain.enums.ClubMemberRole;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubMember extends BaseEntity {

    // 동아리 멤버 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 동아리 권한
    @Enumerated(EnumType.STRING)
    private ClubMemberRole role;

    // 동아리 가입 날짜
    @Column(nullable = false)
    private Date joined_date;
    
    // 동아리 활동 여부
    @Enumerated(EnumType.STRING)
    private ActiveType status;

    // 동아리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    // 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    // 콜라보 글 (LEADER, OFFICER 만 작성할 수 있음)
    @OneToMany(mappedBy = "clubMember", cascade = CascadeType.ALL)
    private List<CollabPost> collabPostList = new ArrayList<>();


    // 보낸 콜라보 요청
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CollabAsk> sendCollabAsks = new HashSet<>();

    // 받은 콜라보 요청
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CollabAsk> receivedCollabAsks = new HashSet<>();





}
