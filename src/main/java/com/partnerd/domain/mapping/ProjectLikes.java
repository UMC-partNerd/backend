package com.partnerd.domain.mapping;

import com.partnerd.domain.Member;
import com.partnerd.domain.ProjectComment;
import com.partnerd.domain.PromotionProjectComment;
import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "project_likes")
public class ProjectLikes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 모집 프로젝트 댓글 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_comment_id")
    private ProjectComment projectComment;

    // 홍보 프로젝트 댓글 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_project_comment_id")
    private PromotionProjectComment promotionProjectComment;
}
