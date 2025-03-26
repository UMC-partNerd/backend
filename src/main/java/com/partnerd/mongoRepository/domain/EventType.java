package com.partnerd.mongoRepository.domain;

import com.partnerd.mongoRepository.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EventType extends BaseEntity {

    // 행사 유형 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유형명
    @Column(nullable = false)
    private String name;

    // 콜라보 글
    @OneToMany(mappedBy = "eventType", fetch = FetchType.LAZY)
    private List<CollabPost> collabPostList = new ArrayList<>();


}
