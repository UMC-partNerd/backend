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
@Table(name = "promotion_project_comment")
public class PromotionProjectComment extends BaseEntity {

    // 포로젝트 홍보 댓글 ID
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

    // 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 프로젝트 홍보 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_project_id")
    private PromotionProject promotionProject;

    // 대댓글 작성을 위한 자기 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private PromotionProjectComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionProjectComment> children = new ArrayList<>();

    public void setPromotionProject(PromotionProject addPromotionProject){
        if (this.promotionProject != null){
            this.promotionProject.getPromotionProjectCommentList().remove(this);
        }
        this.promotionProject = addPromotionProject;
        promotionProject.getPromotionProjectCommentList().add(this);
    }

    public void setMember(Member addMember) {
        if (this.member != null) {
            member.getPromotionProjectCommentList().remove(this);
        }
        this.member = addMember;
        member.getPromotionProjectCommentList().add(this);
    }

    public void addParentComment(PromotionProjectComment promotionProjectComment){
        this.parentComment = promotionProjectComment;
        parentComment.getChildren().add(this);
    }

    public void changeToDeleted(){
        this.isDeleted = true;
    }

}
