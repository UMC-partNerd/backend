package com.partnerd.domain;

import com.partnerd.domain.mapping.PromotionProjectMember;
import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.*;

@Entity
@Getter
@Setter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PromotionProject extends BaseEntity {

    // 포로젝트 홍보 ID
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    // 프로젝트 한줄 소개
    @Column(nullable = false)
    private String intro;

    // 프로필 사진
    private String profile_img_url;

    // 프로젝트 설명
    @Column(nullable = false)
    private String description;

    // 투표수
    @Column(nullable = false)
    private Long vote = 0L;

    //조회수
    @Column(nullable = false)
    private Long views;

    // 컨택트 방법
    @OneToMany(mappedBy = "promotionProject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ContactMethod> contactMethodList = new HashSet<>();

    // 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    
    // 홍보 프로젝트 팀원
    @OneToMany(mappedBy = "promotionProject", cascade = CascadeType.ALL)
    private Set<PromotionProjectMember> promotionProjectMemberList = new HashSet<>();

    // 홍보 프로젝트 사진
    @OneToMany(mappedBy = "promotionProject", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PromotionProjectImage> promotionProjectImageList = new HashSet<>();

    // 홍보 프로젝트 댓글
    @OneToMany(mappedBy = "promotionProject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PromotionProjectComment> promotionProjectCommentList = new LinkedHashSet<>();

    
}
