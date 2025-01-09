package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Personal extends BaseEntity {

    // 퍼스널 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 퍼스널 한줄 소개
    private String intro;

    // 경력
    private String personal_history;

    // 학력
    private String education;

    // 활동 프로젝트
    private String activity_project;

    // 스킬
    private String skill;
}
