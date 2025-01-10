package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.mapping.PromotionProjectMember;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PromotionProject extends BaseEntity {

    // 포로젝트 홍보 ID
    @Id
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
//    @Column(nullable = false)
    private Long vote;

    // 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    
    // 홍보 프로젝트 팀원
    @OneToMany(mappedBy = "promotionProject", cascade = CascadeType.ALL)
    private List<PromotionProjectMember> promotionProjectMemberList = new ArrayList<>();

    // 홍보 프로젝트 사진
    @OneToMany(mappedBy = "promotionProject", cascade = CascadeType.ALL)
    private List<PromotionProjectImage> promotionProjectImageList = new ArrayList<>();

    // 홍보 프로젝트 댓글
    @OneToMany(mappedBy = "promotionProject", cascade = CascadeType.ALL)
    private List<PromotionProjectComment> promotionProjectCommentList = new ArrayList<>();

    
}
