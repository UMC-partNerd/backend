package com.partnerd.domain;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Personal extends BaseEntity {

    // 퍼스널 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 퍼스널 한줄 소개
    private String intro;

    // 경력
    @Column(name = "personal_history")
    private String personalHistory;

    // 학력
    private String education;

    // 활동 프로젝트
    @Column(name = "activity_project")
    private String activityProject;

    // 스킬
    private String skill;

    // 사용자(FK)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member", referencedColumnName = "id", nullable = false)
    private Member member;

    // 퍼스널 링크
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_id")
    private List<PersonalLink> personalLinkList = new ArrayList<>();

    public void addLink(PersonalLink link) {
        personalLinkList.add(link);
    }

    // 멤버 설정 메서드
    public void setMember(Member member) {
        if (member == null) {
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        this.member = member;
    }
}
