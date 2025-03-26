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
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class PromotionProjectMember extends BaseEntity {

    // 홍보 프로젝트 팀원 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 ID (FK)
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 프로젝트 홍보 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_project_id")
    private PromotionProject promotionProject;

    // setter (프로젝트 홍보 생성)
    public void setPromotionProject(PromotionProject promotionProject){
        if (this.promotionProject != null){
            promotionProject.getPromotionProjectMemberList().remove(this);
        }
        this.promotionProject = promotionProject;
        promotionProject.getPromotionProjectMemberList().add(this);
    }

}
