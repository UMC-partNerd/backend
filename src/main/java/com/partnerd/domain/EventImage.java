package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.enums.ImageType;
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
    private ImageType image_type;

    // 이미지 url
    @Column(nullable = false)
    private String image_url;
}
