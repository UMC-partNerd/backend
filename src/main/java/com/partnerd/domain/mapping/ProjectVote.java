package com.partnerd.domain.mapping;

import com.partnerd.domain.Member;
import com.partnerd.domain.PromotionProject;
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
public class ProjectVote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 홍보 프로젝트 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_project_id")
    private PromotionProject promotionProject;
}
