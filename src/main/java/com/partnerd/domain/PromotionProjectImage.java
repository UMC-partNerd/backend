package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PromotionProjectImage extends BaseEntity {

    // 프로젝트 홍보 서비스 사진 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이미지 url
    @Column(nullable = false)
    private String image_url;

    // 프로젝트 홍보 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_project_id")
    private PromotionProject promotionProject;
}
