package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.enums.SocialType;
import com.partnerd.domain.mapping.*;
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
public class Member extends BaseEntity {

    // 사용자ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 소셜 ID (소셜 로그인 사용자 고유 ID)
    @Column(nullable = true, unique = true) // 소셜 사용자를 고유하게 식별
    private String socialId;

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
    @Column(nullable = true) // 소셜 로그인 사용자는 비밀번호가 없음
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agreement_id", nullable = false, unique = true)
    private Agreements agreement;

    // 문의글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CollabInquiry> collabInquiryList = new ArrayList<>();

    // 프로젝트
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Project> projectList = new ArrayList<>();

    // 프로젝트 팀원
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ProjectMember> projectMemberList = new ArrayList<>();

    // 프로젝트 댓글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectComment> projectCommentList = new ArrayList<>();

    // 프로젝트 홍보
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PromotionProject> promotionProjectList = new ArrayList<>();

    // 프로젝트 홍보 팀원
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PromotionProjectMember> promotionProjectMemberList = new ArrayList<>();

    // 프로젝트 홍보 댓글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionProjectComment> promotionProjectCommentList = new ArrayList<>();

    // 동아리
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ClubMember> clubMembers = new ArrayList<>();

    // 보낸 동아리 가입 요청
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<ClubMembershipRequest> sendClubMembershipRequests = new ArrayList<>();

    // 작성한 커뮤니티 글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Community> communityList = new ArrayList<>();

    // 커뮤니티 댓글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityComment> communityCommentList = new ArrayList<>();



    @PrePersist
    public void setDefaultValues() {
        if (this.name == null) this.name = "임시 이름";
        if (this.nickname == null) this.nickname = "임시 닉네임";
        if (this.birth == null) this.birth = new Date(0);
        if (this.email == null) this.email = "unknown@example.com";
    }
}
