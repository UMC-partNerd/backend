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
public class ClubImage extends BaseEntity {

    // 동아리 이미지 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이미지 유형
    @Enumerated(EnumType.STRING)
    private ImageType image_type;


    //이미지 키네임
    @Column(name = "key_name", nullable = false)
    private String keyName;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
}
