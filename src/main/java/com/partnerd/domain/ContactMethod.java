package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ContactMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연락 유형
    @Column(name = "contact_type", nullable = false)
    private String contactType;

    // 연락 링크
    @Column(name = "contact_url", nullable = false)
    private String contactUrl;

    //동아리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    // 콜라보 글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collab_post_id")
    private CollabPost collabPost;

    public void setCollabPost(CollabPost collabPost) {
        if(this.collabPost != null) {
            this.collabPost.getContactMethodList().remove(this);
        }
        this.collabPost = collabPost;
        this.collabPost.getContactMethodList().add(this);
    }

    public void setClub(Club club) { //양방향연관관계설정
        if (this.club != null) {
            this.club.getContactMethodList().remove(this);
        }
        this.club = club;
        if (club != null) {
            this.club.getContactMethodList().add(this);
        }
    }
}
