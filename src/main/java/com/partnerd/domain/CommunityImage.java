package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "community_image")
public class CommunityImage extends BaseEntity {

    // 커뮤니티 사진 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이미지 url
    @Column(nullable = false)
    private String keyName;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    public void setCommunity(Community community) {
        if(this.community != null) {
            this.community.getCommunityImageList().remove(this);
        }

        this.community = community;
        community.getCommunityImageList().add(this);
    }


}
