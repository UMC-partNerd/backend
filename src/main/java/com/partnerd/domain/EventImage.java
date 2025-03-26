package com.partnerd.domain;

import com.partnerd.domain.enums.ImageType;
import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EventImage extends BaseEntity {

    // 행사 이미지 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이미지 유형
    @Enumerated(EnumType.STRING)
    @Column(name = "image_type", nullable = false)
    private ImageType imageType;

    // 이미지 url
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
}
