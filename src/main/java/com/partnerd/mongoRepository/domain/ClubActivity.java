package com.partnerd.mongoRepository.domain;

import com.partnerd.mongoRepository.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.List;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false, unique = true)
    private Club club;

    // 활동사진
    @OneToMany(mappedBy = "clubActivity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClubActivityImage> clubActivityImageList = new LinkedHashSet<>();

    //  clubActivityImageList가 null일 경우 빈 LinkedHashSet으로 초기화
    public Set<ClubActivityImage> getClubActivityImageList() {
        if (this.clubActivityImageList == null) {
            this.clubActivityImageList = new LinkedHashSet<>();
        }
        return this.clubActivityImageList;
    }

    //  연관관계 편의 메서드 추가
    public void setClub(Club club) {
        this.club = club;
    }

    // 기존 활동 내용 업데이트
    public void updateActivity(String newIntro) {
        this.intro = newIntro;
    }

    // 기존 활동 이미지 삭제
    public void clearActivityImages() {
        this.clubActivityImageList.clear();
    }


    public void addActivityImages(List<String> keyNames) {
        keyNames.forEach(keyName ->
                this.clubActivityImageList.add(
                        ClubActivityImage.builder()
                                .keyName(keyName)
                                .clubActivity(this)
                                .build()
                )
        );
    }



}