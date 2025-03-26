package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Alarm extends BaseEntity {

    // 알림 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 수신 여부
    @Column(nullable = false)
    private Boolean is_confirmed;

    // 알림 타입
    @Column(nullable = false)
    private String dtype;
    
    // 제목
    @Column(nullable = false)
    private String title;
    
    // 내용
    @Column(nullable = false)
    private String body;
}
