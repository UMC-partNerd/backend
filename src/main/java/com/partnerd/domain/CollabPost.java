package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CollabPost extends BaseEntity {

    // 콜라보글ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 콜라보 제목
    @Column(nullable = false)
    private String title;

    // 콜라보 한줄 평
    @Column(nullable = false)
    private String intro;

    // 개최 희망 날짜
    @Column(nullable = false)
    private Date open_date;

    // 개최 종료 날짜
    private Date close_date;

    // 콜라보 모집 시작 날짜
    @Column(nullable = false)
    private Date start_date;

    // 콜라보 모집 마감 날짜
    @Column(nullable = false)
    private Date end_date;

    // 콜라보 희망 대상
    @Column(nullable = false)
    private String collab_target;

    // 온/오프라인 모드
    @Column(nullable = false)
    private Long event_mode;

    // 콜라보 설명
    @Column(nullable = false)
    private String description;

    // 컨택트 방식
    @Column(nullable = false)
    private String contact_method;



}
