package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "community_comment")
public class CommunityComment extends BaseEntity {

    // 커뮤니티 댓글 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 내용
    @Column(nullable = false)
    private String contents;

    // 좋아요 수
    @Column(nullable = false)
    private Long likes = 0L;

    // 삭제 여부
    @Column(nullable = false)
    private Boolean isDeleted = false;

    // 커뮤니티 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    // 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 대댓글 작성을 위한 자기 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_comment_id")
    private CommunityComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityComment> children = new ArrayList<>();

    public void setCommunity(Community addCommunity) {
        if (this.community != null) {
            community.getCommunityCommentList().remove(this);
        }
        this.community = addCommunity;
        community.getCommunityCommentList().add(this);
    }

    public void setMember(Member addMember) {
        if (this.member != null) {
            member.getCommunityCommentList().remove(this);
        }
        this.member = addMember;
        member.getCommunityCommentList().add(this);
    }

    public void addParentComment(CommunityComment communityComment){
        this.parentComment = communityComment;
        parentComment.getChildren().add(this);
    }

    public void changeToDeleted(){
        this.isDeleted = true;
    }

}
