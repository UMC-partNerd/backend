package com.partnerd.domain.mapping;

import com.partnerd.domain.Community;
import com.partnerd.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommunityLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 커뮤니티 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    public void setCommunity(Community community) {
        if(this.community != null) {
            this.community.getCommunityLikesList().remove(this);
        }
        this.community = community;
        community.getCommunityLikesList().add(this);
    }


}
