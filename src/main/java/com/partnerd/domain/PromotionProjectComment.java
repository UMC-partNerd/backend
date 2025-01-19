package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PromotionProjectComment extends BaseEntity {

    // 포로젝트 홍보 댓글 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 내용
    @Column(nullable = false)
    private String content;

    // 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 프로젝트 홍보 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_project_id")
    private PromotionProject promotionProject;
}
