package com.partnerd.domain;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CollabPostHandler;
import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.domain.mapping.CollabAsk;
import com.partnerd.domain.mapping.CollabPostCategory;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.*;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
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
    @Column(name = "open_date",nullable = false)
    private Date openDate;

    // 개최 종료 날짜
    @Column(name = "close_date",nullable = false)
    private Date closeDate;

    // 콜라보 모집 시작 날짜
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    // 콜라보 모집 마감 날짜
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    // 콜라보 희망 대상
    @Column(name = "collab_target", nullable = false)
    private String collabTarget;

    // 온/오프라인 모드
    @Column(name = "event_mode", nullable = false)
    private int eventMode;

    // 콜라보 설명
    @Column(nullable = false)
    private String description;

    // 콜라보 유형
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_type_id")
    private EventType eventType;

    // 컨텍드 방법
    @OneToMany(mappedBy = "collabPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ContactMethod> contactMethodList = new LinkedHashSet<>();
    
    // 콜라보 카테고리
    @OneToMany(mappedBy = "collabPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CollabPostCategory> collabPostCategoryList = new LinkedHashSet<>();

    // 콜라보 문의글
    @Setter
    @OneToMany(mappedBy = "collabPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CollabInquiry> collabInquiryList = new ArrayList<>();

    // 배너 및 메인, 행사 사진
    @OneToMany(mappedBy = "collabPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CollabPostImg> collabPostImgList = new LinkedHashSet<>();

    // 콜라보 요청
    @OneToMany(mappedBy = "collabPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CollabAsk> collabAskList = new ArrayList<>();

    public void setClubMember(ClubMember addClubMember) {
        if (this.clubMember != null) {
            this.clubMember.getCollabPostList().remove(this);
        }
        this.clubMember = addClubMember;
        clubMember.getCollabPostList().add(this);
    }

    public void setEventType(EventType addEventType) {
        if (this.eventType != null) {
            this.eventType.getCollabPostList().remove(this);
        }
        this.eventType = addEventType;
        eventType.getCollabPostList().add(this);
    }

    public void updateCollabPost(CollabPostRequestDTO.RequestCollabPostDTO requestDTO, EventType eventType) {
        this.title = requestDTO.getTitle();
        this.intro = requestDTO.getIntro();
        this.openDate = requestDTO.getOpenDate();
        this.closeDate = requestDTO.getCloseDate();
        this.startDate = requestDTO.getStartDate();
        this.endDate = requestDTO.getEndDate();
        this.collabTarget = requestDTO.getCollabTarget();
        this.eventType = eventType;
        this.eventMode = requestDTO.getEventMode();
        this.description = requestDTO.getDescription();
    }

    public void validateAuthor(Long memberId) {
        if (!this.clubMember.getMember().getId().equals(memberId)) {
            throw new CollabPostHandler(ErrorStatus.COLLAB_POST_NOT_AUTHOR);
        }
    }

}
