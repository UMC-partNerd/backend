package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubActivityImage extends BaseEntity {

    // 활동 이미지 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_activity_id", nullable = false)
    private ClubActivity clubActivity;

    @Column(name = "key_name", nullable = false)
    private String keyName;

    // 연관관계 편의 메서드 추가
    public void setClubActivity(ClubActivity clubActivity) {
        this.clubActivity = clubActivity;
    }



}
