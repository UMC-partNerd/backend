package com.partnerd.domain;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CommunityHandler;
import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.mapping.CommunityLikes;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Community extends BaseEntity {

    // 카테고리 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(nullable = false)
    private String content;

    // 커뮤니티 댓글
    @OneToMany(mappedBy = "community", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommunityComment> communityCommentList = new LinkedHashSet<>();

    // 좋아요
    @Column(nullable = false)
    private int likes;

    // 작성자
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityImage> communityImageList = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityLikes> communityLikesList  = new ArrayList<>();

    public void setMember(Member member) {
        if(this.member != null) {
            this.member.getCommunityList().remove(this);
        }

        this.member = member;
        member.getCommunityList().add(this);
    }

    public void updateCommunity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validatorAuthor(Long memberId) {
        if (this.getMember().getId() != memberId) {
            throw new CommunityHandler(ErrorStatus.COMMUNITY_NOT_AUTHOR);
        }
    }

    public void addLikes () {
        this.likes += 1;
    }

    public void removeLikes () {
        this.likes -= 1;
    }

}
