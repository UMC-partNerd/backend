package com.partnerd.collabPost;

import com.partnerd.apiPaylaod.exception.handler.CollabPostHandler;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.domain.Category;
import com.partnerd.domain.Club;
import com.partnerd.domain.CollabPost;
import com.partnerd.domain.EventType;
import com.partnerd.domain.enums.ActiveType;
import com.partnerd.domain.enums.ClubMemberRole;
import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.repository.categoryRepository.CategoryRepository;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepository;
import com.partnerd.repository.collabPostRepository.collabPost.CollabPostImgRepository;
import com.partnerd.repository.collabPostRepository.collabPost.CollabPostRepository;
import com.partnerd.repository.collabPostRepository.collabPost.EventTypeRepository;
import com.partnerd.service.collabPostService.impl.CollabPostCommandServiceImpl;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CollabPostCommandServiceTest {
    @InjectMocks
    private CollabPostCommandServiceImpl collabPostCommandService;

    @Mock
    private CollabPostRepository collabPostRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

    @Mock
    private ClubMemberRepository clubMemberRepository;

    @Mock
    private CollabPostImgRepository collabPostImgRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private CollabPostRequestDTO.RequestCollabPostDTO requestDTO;
    private Long memberId;
    private Club club;
    private ClubMember clubMember;


    @BeforeEach
    void setUp() {
        memberId = 1L;

        // 1. 클럽 생성
        club = Club.builder()
                .id(1L)
                .name("테스트 클럽")
                .intro("테스트 클럽 소개")
                .views(0L)
                .category(new Category(1L, "IT", new ArrayList<>(), new ArrayList<>()))
                .build();

        // 2. 클럽 멤버 생성 (리더)
        clubMember = ClubMember.builder()
                .id(1L)
                .role(ClubMemberRole.LEADER)
                .status(ActiveType.ACTIVE)
                .joined_date(new Date())
                .club(club)  // ✅ 클럽 연결
                .build();

        requestDTO = CollabPostRequestDTO.RequestCollabPostDTO.builder()
                .title("콜라보 제목")
                .intro("콜라보 소개")
                .eventTypeId(1L)
                .categoryIds(List.of(1L, 2L))
                .bannerKeyName("collabPost/BANNER/image.png")
                .mainKeyName("collabPost/MAIN/image.png")
                .eventImgKeyNameList(List.of("collabPost/EVENT/event1.png", "collabPost/EVENT/event2.png"))
                .build();
    }


    @Test
    @DisplayName("콜라보 글 등록")
    void createCollabPost() {
        // given
        when(clubMemberRepository.findByMember_idAndRole(memberId, ClubMemberRole.LEADER)).thenReturn(Optional.of(clubMember));
        when(categoryRepository.findAllByIdWithCollabPostCategory(requestDTO.getCategoryIds()))
                .thenReturn(List.of(new Category(1L, "웹/앱 개발",new ArrayList<>(), new ArrayList<>()),
                        new Category(5L, "디자인", new ArrayList<>(), new ArrayList<>())));
        when(eventTypeRepository.findById(requestDTO.getEventTypeId()))
                .thenReturn(Optional.of(new EventType(3L, "대회/공모전", new ArrayList<>())));

        when(collabPostRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when ( 콜라보 글 등록 할 때)
        CollabPost collabPost = collabPostCommandService.addCollabPost(requestDTO, memberId);


        // then (콜라보 글 등록 후)
        assertThat(collabPost).isNotNull();
        assertThat(collabPost.getEventType().getName()).isEqualTo("대회/공모전");  // ✅ 이벤트 타입 확인
        assertThat(collabPost.getCollabPostCategoryList().size()).isEqualTo(2);  // ✅ 카테고리 개수 확인

    }

    @Test
    @DisplayName("클럽멤버의 리더진이 아니면 예외 발생")
    void checkClubMember() {

        // given
        when(clubMemberRepository.findByMember_idAndRole(memberId, ClubMemberRole.LEADER)).thenReturn(Optional.empty());

        CollabPostHandler thrownException = (CollabPostHandler) catchThrowable(() ->
                collabPostCommandService.addCollabPost(requestDTO, memberId)
        );

        assertThat(thrownException).isInstanceOf(CollabPostHandler.class);
        assertThat(thrownException.getErrorReason().getCode()).isEqualTo("COLLABPOST4030");
        assertThat(thrownException.getErrorReason().getMessage()).isEqualTo("동아리 리더만 콜라보 글을 작성할 수 있습니다.");
    }

    
}





