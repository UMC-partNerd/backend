package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.mapping.ProjectCategoryPrefer;
import com.partnerd.domain.mapping.ProjectMember;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
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

    // 프로젝트 설명
    @Column(nullable = false)
    private String description;

    // 현재 개발 및 발전 상황
    @Column(nullable = false)
    private String current_progress;

    // 필요한 역량
//    @Column(nullable = false)
    private String skill;

    // 필요한 파트
    @Column(nullable = false)
    private String part;

    // 모집 인원
    private String recruitNum;

    // 개발 기술 스택
    @Column(nullable = false)
    private String dev_stack;

    // 기획 기술 스택
    @Column(nullable = false)
    private String pm_stack;

    // 디자인 기술 스택
    @Column(nullable = false)
    private String design_stack;

    // 시작 날짜
    @Column(nullable = false)
    private Date startDate;

    // 종료 날짜
    @Column(nullable = false)
    private Date endDate;
    
    // 컨택트 방법
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ContactMethod> contactMethodList = new HashSet<>();

    // 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 프로젝트 팀원
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<ProjectMember> projectMemberList = new HashSet<>();

    // 프로젝트 사진
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectImage> projectImageList = new HashSet<>();

    // 프로젝트 카테고리
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<ProjectCategoryPrefer> projectCategoryPreferList = new HashSet<>();

    // 프로젝트 댓글
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectComment> projectCommentList = new LinkedHashSet<>();

}
