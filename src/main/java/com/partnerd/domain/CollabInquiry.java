package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CollabInquiry extends BaseEntity {

    // 콜라보 문의 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 문의 내용
    @Column(nullable = false)
    private String contents;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 콜라보 글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collab_post_id")
    private CollabPost collabPost;


    // 대댓글을 위한 자기 참조 관계 (parent inquiry)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_inquiry_id")
    private CollabInquiry parentInquiry;

    // 댓글 좋아요 수
    private int likes = 0;

    // 삭제 여부
    private Integer isDeleted = 0;


    @OneToMany(mappedBy = "parentInquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CollabInquiry> children = new ArrayList<>();


    public void setCollabPost(CollabPost collabPost) {
        if (this.collabPost != null) {
            this.collabPost.getCollabInquiryList().remove(this);
        }
        this.collabPost = collabPost;
        collabPost.getCollabInquiryList().add(this);
    }

    public void setMember(Member addMember) {
        if (this.member != null) {
            member.getCollabInquiryList().remove(this);
        }
        this.member = addMember;
        member.getCollabInquiryList().add(this);
    }

    public void addParentInquiry(CollabInquiry collabInquiry) {
        this.parentInquiry = collabInquiry;
        parentInquiry.getChildren().add(this);
    }


    public void updateCollabInquiry(String contents) {
        this.contents = contents;
    }

    public void changeIsDeleted() {
        this.isDeleted = 1;
    }




}
