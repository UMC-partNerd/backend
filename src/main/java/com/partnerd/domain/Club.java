package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Club extends BaseEntity {

    // 동아리 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 동아리명
    @Column(nullable = false)
    private String name;

    // 동아리 한줄 설명
    @Column(nullable = false)
    private String intro;

    // 동아리 설명
    @Column(nullable = false)
    private String profile;

    // 조회수
    @Column(nullable = false)
    private Long views;

    // 컨택트 방식
    @Column(nullable = false)
    private String contact_method;
}
