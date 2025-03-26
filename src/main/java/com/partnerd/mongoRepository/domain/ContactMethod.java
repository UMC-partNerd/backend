package com.partnerd.mongoRepository.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;

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

    // 프로젝트 모집
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    // 프로젝트 홍보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_project_id")
    private PromotionProject promotionProject;
    
    public void setCollabPost(CollabPost collabPost) {
        if(this.collabPost != null) {
            this.collabPost.getContactMethodList().remove(this);
        }
        this.collabPost = collabPost;
        this.collabPost.getContactMethodList().add(this);
    }

    public void setClub(Club club) {
        if (this.club != null) {
            this.club.getContactMethodList().remove(this);
        }
        this.club = club;

        if (club != null && !club.getContactMethodList().contains(this)) {
            club.getContactMethodList().add(this);
        }
    }

    // 프로젝트 모집
    public void setProject(Project project) {
        if (this.project != null) {
            if (this.project.getContactMethodList() != null) {
                this.project.getContactMethodList().remove(this);
            }
        }
        this.project = project;
        if (this.project != null) {
            if (this.project.getContactMethodList() == null) {
                this.project.setContactMethodList(new HashSet<>());
            }
            this.project.getContactMethodList().add(this);
        }
    }

    // 프로젝트 홍보
    public void setPromotionProject(PromotionProject promotionProject) {
        if (this.promotionProject != null) {
            if (this.promotionProject.getContactMethodList() != null) {
                this.promotionProject.getContactMethodList().remove(this);
            }
        }
        this.promotionProject = promotionProject;
        if (this.promotionProject != null) {
            if (this.promotionProject.getContactMethodList() == null) {
                this.promotionProject.setContactMethodList(new HashSet<>());
            }
            this.promotionProject.getContactMethodList().add(this);
        }
    }
}
