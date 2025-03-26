package com.partnerd.domain;

import com.partnerd.domain.enums.ImageType;
import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;

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

    @Column(name = "key_name", nullable = false)
    private String keyName;

    @Column(name = "image_type", nullable = false)
    private ImageType imageType;

    // 프로젝트 홍보 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_project_id")
    private PromotionProject promotionProject;

    public void setPromotionProject(PromotionProject promotionProject){
        if (this.promotionProject != null){
            if (this.promotionProject.getPromotionProjectImageList() != null){
                this.promotionProject.getPromotionProjectImageList().remove(this);
            }
        }
        this.promotionProject = promotionProject;
        if (this.promotionProject != null){
            if (this.promotionProject.getPromotionProjectImageList() == null){
                this.promotionProject.setPromotionProjectImageList(new HashSet<>());
            }
            this.promotionProject.getPromotionProjectImageList().add(this);
        }
    }
}
