package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.enums.ActiveType;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubActivity extends BaseEntity {

    // 활동 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 활동 소개
    @Column(nullable = false)
    private String intro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    // 활동사진
    @OneToMany(mappedBy = "clubActivity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClubActivityImage> clubActivityImageList = new LinkedHashSet<>();



}