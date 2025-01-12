package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.enums.ProjectStatus;
import com.partnerd.domain.mapping.ProjectCategoryPrefer;
import com.partnerd.domain.mapping.ProjectMember;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Project extends BaseEntity {

    // 포로젝트ID
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

    // 모집 상태
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    // 프로젝트 설명
    @Column(nullable = false)
    private String description;

    // 현재 개발 및 발전 상황
    @Column(nullable = false)
    private String current_progress;

    // 필요한 역량
    @Column(nullable = false)
    private String skill;

    // 필요한 파트
    @Column(nullable = false)
    private String part;

    // 개발 기술 스택
    @Column(nullable = false)
    private String dev_stack;

    // 기획 기술 스택
    @Column(nullable = false)
    private String pm_stack;

    // 디자인 기술 스택
    @Column(nullable = false)
    private String design_stack;

    // 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 프로젝트 팀원
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectMember> projectMemberList = new ArrayList<>();

    // 프로젝트 사진
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectImage> projectImageList = new ArrayList<>();

    // 프로젝트 카테고리
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectCategoryPrefer> projectCategoryPreferList = new ArrayList<>();

    // 프로젝트 댓글
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectComment> projectCommentList = new ArrayList<>();

}
