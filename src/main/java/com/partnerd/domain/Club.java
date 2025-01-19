package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.mapping.ClubMember;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String intro;

    @Column(nullable = true)
    private String profile; // 프로필 이미지 (null 가능)

    //조회수
    @Column(nullable = false)
    private Long views;

    //컨택트방법
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ContactMethod> contactMethodList = new ArrayList<>();

    //카테고리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    //동아리회원
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ClubMember> clubMembers = new ArrayList<>();


    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubActivity> activities = new ArrayList<>();

    // 클럽 정보를 업데이트하는 메서드
    public void update(String name, String intro, Category category) {
        this.name = name;
        this.intro = intro;
        this.category = category ;

    }

    public void updateContactMethods(List<ContactMethod> contactMethodList) {

        //기존 콘택트메서드 제거
        this.contactMethodList.clear();

        //새로운 콘택트메서드 추가
        this.contactMethodList.addAll(contactMethodList);

        // 양방향 관계 설정
        contactMethodList.forEach(contactMethod -> contactMethod.setClub(this));
    }


}
