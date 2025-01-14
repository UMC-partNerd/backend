package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.enums.CollabInquiryStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CollabInquiry extends BaseEntity {

    // 콜라보 문의 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 문의 내용
    @Column(nullable = false)
    private String contents;

    // 문의 상태
    @Enumerated(EnumType.STRING)
    private CollabInquiryStatus status;

    // 비밀글 여부
    @Column(name = "is_secret",nullable = false)
    private Boolean isSecret;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
