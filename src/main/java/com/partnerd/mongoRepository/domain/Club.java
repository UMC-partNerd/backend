package com.partnerd.mongoRepository.domain;

import com.partnerd.mongoRepository.domain.common.BaseEntity;
import com.partnerd.mongoRepository.domain.enums.ActiveType;
import com.partnerd.mongoRepository.domain.enums.ClubMemberRole;
import com.partnerd.mongoRepository.domain.enums.ImageType;
import com.partnerd.mongoRepository.domain.mapping.ClubMember;
import com.partnerd.mongoRepository.domain.mapping.ClubMembershipRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Club extends BaseEntity{

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String intro;

    //조회수
    @Column(nullable = false)
    private Long views =0L;

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

    // 배너 및 메인, 행사 사진
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClubImage> clubImgList = new LinkedHashSet<>();

    //동아리활동
    @OneToOne(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ClubActivity activity;

    // 동아리 가입 요청 리스트
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMembershipRequest> receivedClubMembershipRequests = new ArrayList<>();

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

    // 연관관계 편의 메서드 추가


    // 🔥 연관관계 편의 메서드
    public void addContactMethod(ContactMethod contactMethod) {
        this.contactMethodList.add(contactMethod);
        contactMethod.setClub(this);
    }

    public void addClubImage(String keyName, ImageType type) {
        ClubImage image = ClubImage.builder()
                .keyName(keyName)
                .image_type(type)
                .club(this)
                .build();
        this.clubImgList.add(image);
    }

    public void addMember(Member member, ClubMemberRole role) {
        ClubMember clubMember = ClubMember.builder()
                .role(role)
                .status(ActiveType.ACTIVE)
                .joined_date(new Date())
                .club(this)
                .member(member)
                .build();
        this.clubMembers.add(clubMember);
    }

    public void addActivity(ClubActivity activity) {
        this.activity = activity;
        activity.setClub(this);
    }

}
