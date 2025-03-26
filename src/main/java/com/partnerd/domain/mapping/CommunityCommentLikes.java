package com.partnerd.domain.mapping;

import com.partnerd.domain.CommunityComment;
import com.partnerd.domain.Member;
import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class CommunityCommentLikes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 커뮤니티 댓글 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_comment_id")
    private CommunityComment communityComment;

}
