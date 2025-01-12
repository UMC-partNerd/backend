package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.enums.SocialType;
import com.partnerd.domain.mapping.ProjectMember;
import com.partnerd.domain.mapping.PromotionProjectMember;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    // 사용자ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이름(설명)
    @Column(nullable = false)
    private String name;

    // 생년월일
    @Column(nullable = false)
    private Date birth;

    // 닉네임
    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 프로필 사진
    private String profile_url;

    // 소셜 타입
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    // 관심 직군
    private String occupation_of_interest;

    // 소속
    private String belong_to_club;

  
  
  
  
  
  
  
  
  
  
    // 약관
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Agreements agreement;

    // 프로젝트
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Project> projectList = new ArrayList<>();

    // 프로젝트 팀원
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ProjectMember> projectMemberList = new ArrayList<>();

    // 프로젝트 댓글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ProjectComment> projectCommentList = new ArrayList<>();

    // 프로젝트 홍보
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PromotionProject> promotionProjectList = new ArrayList<>();

    // 프로젝트 홍보 팀원
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PromotionProjectMember> promotionProjectMemberList = new ArrayList<>();

    // 프로젝트 홍보 댓글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PromotionProjectComment> promotionProjectCommentList = new ArrayList<>();
}
