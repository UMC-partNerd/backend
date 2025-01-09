package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PromotionProject extends BaseEntity {

    // 포로젝트 홍보 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    // 프로젝트 한줄 소개
    @Column(nullable = false)
    private String intro;

    // 프로필 사진
    private String profile_img_url;

    // 프로젝트 설명
    @Column(nullable = false)
    private String description;

    // 투표수
    @Column(nullable = false)
    private Long vote;
}
