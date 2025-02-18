package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Community extends BaseEntity {

    // 카테고리 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(nullable = false)
    private String content;

    // 좋아요
    @Column(nullable = false)
    private int likes;

    // 작성자
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityImage> communityImageList = new ArrayList<>();

    public void setMember(Member member) {
        if(this.member != null) {
            this.member.getCommunityList().remove(this);
        }

        this.member = member;
        member.getCommunityList().add(this);
    }

    public void updateCommunity(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
