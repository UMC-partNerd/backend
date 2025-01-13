package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.domain.mapping.CollabPostCategory;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CollabPost extends BaseEntity {

    // 콜라보글ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 콜라보 글 작성자 (팀 멤버)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

    // 콜라보 제목
    @Column(nullable = false)
    private String title;

    // 콜라보 한줄 평
    @Column(nullable = false)
    private String intro;

    // 개최 희망 날짜
    @Column(nullable = false)
    private Date open_date;

    // 개최 종료 날짜
    private Date close_date;

    // 콜라보 모집 시작 날짜
    @Column(nullable = false)
    private Date start_date;

    // 콜라보 모집 마감 날짜
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    // 콜라보 희망 대상
    @Column(nullable = false)
    private String collab_target;

    // 온/오프라인 모드
    @Column(nullable = false)
    private int event_mode;

    // 콜라보 설명
    @Column(nullable = false)
    private String description;

    // 콜라보 유형
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_type_id")
    private EventType eventType;

    // 컨텍드 방법
    @OneToMany(mappedBy = "collabPost", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ContactMethod> contactMethodList = new ArrayList<>();

    // 콜라보 카테고리
    @OneToMany(mappedBy = "collabPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CollabPostCategory> collabPostCategoryList = new ArrayList<>();

    public void setClubMember(ClubMember clubMember) {
        if (this.clubMember != null) {
            this.clubMember.getCollabPostList().remove(this);
        }
        this.clubMember = clubMember;
        clubMember.getCollabPostList().add(this);
    }

    public void setEventType(EventType eventType) {
        if (this.eventType != null) {
            this.eventType.getCollabPostList().remove(this);
        }
        this.eventType = eventType;
        eventType.getCollabPostList().add(this);
    }

    public void updateCollabPost(CollabPostRequestDTO.RequestCollabPostDTO requestDTO, EventType eventType) {
        this.title = requestDTO.getTitle();
        this.intro = requestDTO.getIntro();
        this.open_date = requestDTO.getOpenDate();
        this.close_date = requestDTO.getCloseDate();
        this.start_date = requestDTO.getStartDate();
        this.endDate = requestDTO.getEndDate();
        this.collab_target = requestDTO.getCollabTarget();
        this.eventType = eventType;
        this.event_mode = requestDTO.getEventMode();
        this.description = requestDTO.getDescription();
    }

}
