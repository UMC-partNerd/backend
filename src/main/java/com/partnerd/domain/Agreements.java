package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Agreements extends BaseEntity {

    // 약관 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 나이
    @Column(nullable = false)
    private Boolean is_adult;
    
    // 약관 동의
    @Column(nullable = false)
    private Boolean terms_of_services;
    
    // 개인 정보
    @Column(nullable = false)
    private Boolean personal_info_usage;
    
    // 선택 정보
    private Boolean optional_info_usage;
    
    // 마케팅 활용
    private Boolean marketing_consent;
    
    // 마케팅 알림
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean marketing_notify;

    // 사용자 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;
}
