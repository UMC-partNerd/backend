package com.partnerd.repository.clubRepository;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ClubHandler;
import com.partnerd.domain.*;
import com.partnerd.domain.enums.ClubMemberRole;
import com.partnerd.domain.enums.ImageType;
import com.partnerd.domain.mapping.QClubMember;
import com.partnerd.web.dto.clubDTO.*;
import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import com.partnerd.web.dto.homeDTO.response.HomeClubDTO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClubRepositoryCustomImpl implements ClubRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<HomeClubDTO> findTopClubs(Pageable pageable) {
        QClub club = QClub.club;
        QClubImage clubImage = QClubImage.clubImage;
        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.constructor(HomeClubDTO.class,
                        club.id,
                        clubImage.keyName,
                        club.name,
                        club.intro,
                        category.name))
                .from(club)
                .join(club.category, category)
                .leftJoin(club.clubImgList, clubImage)
                .on(clubImage.image_type.eq(ImageType.MAIN)) // 🔥 MAIN 이미지 필터링
                .where(clubImage.id.isNull().or(clubImage.image_type.eq(ImageType.MAIN)))
                .orderBy(club.views.desc()) // 🔥 조회수 높은 순으로 정렬
                .limit(pageable.getPageSize()) // 🔥 상위 3개 제한
                .fetch();
    }

    @Override
    public List<ClubDTO> findClubsByFilters(Integer page, String sort, Long categoryID) {
        QClub club = QClub.club;
        QClubImage clubImage = QClubImage.clubImage;

        JPAQuery<ClubDTO> query = queryFactory
                .select(Projections.constructor(ClubDTO.class,
                        club.id,
                        clubImage.keyName, // null 허용
                        club.name,
                        club.intro
                ))
                .from(club)
                .leftJoin(club.clubImgList, clubImage)
                .on(clubImage.image_type.eq(ImageType.MAIN)) // MAIN 이미지만 가져오기
                .where(categoryID != null && categoryID > 0 ? club.category.id.eq(categoryID) : null) // 카테고리 필터
                .orderBy(
                        "latest".equalsIgnoreCase(sort) ? club.createdAt.desc() : club.views.desc()
                )
                .offset(page * 12) // 페이지네이션 적용
                .limit(12);

        return query.fetch();
    }

    @Override
    public ClubDetailResponseDTO findClubDetails(Long clubId, Long memberId) {
        QClub club = QClub.club;
        QClubImage clubImage = QClubImage.clubImage;
        QClubMember clubMember = QClubMember.clubMember;
        QMember member = QMember.member;
        QContactMethod contactMethod = QContactMethod.contactMethod;
        QClubActivity clubActivity = QClubActivity.clubActivity;
        QClubActivityImage clubActivityImage = QClubActivityImage.clubActivityImage;
        QCollabPost collabPost = QCollabPost.collabPost;

        // 1. 클럽 기본 정보 + 리더 여부 조회
        Tuple result = queryFactory
                .select(
                        club.id,
                        club.name,
                        club.category.name,
                        club.intro,
                        clubMember.role,
                        clubMember.member.id
                )
                .from(club)
                .leftJoin(club.clubMembers, clubMember)
                .on(clubMember.member.id.eq(memberId))
                .where(club.id.eq(clubId))
                .fetchOne();

        if (result == null) {
            throw new ClubHandler(ErrorStatus.CLUB_NOT_FOUND);
        }

        Long clubIdResult = result.get(club.id);
        String clubName = result.get(club.name);
        String categoryName = result.get(club.category.name);
        String intro = result.get(club.intro);
        boolean isLeader = result.get(clubMember.role) == ClubMemberRole.LEADER || result.get(clubMember.role) == ClubMemberRole.OFFICER;

        // 2. 프로필 이미지 & 배너 이미지 조회
        String profileImage = queryFactory
                .select(clubImage.keyName)
                .from(clubImage)
                .where(clubImage.club.id.eq(clubId)
                        .and(clubImage.image_type.eq(ImageType.MAIN)))
                .orderBy(clubImage.createdAt.desc()) // 최신 이미지 선택
                .fetchOne();

        String bannerImage = queryFactory
                .select(clubImage.keyName)
                .from(clubImage)
                .where(clubImage.club.id.eq(clubId)
                        .and(clubImage.image_type.eq(ImageType.BANNER)))
                .orderBy(clubImage.createdAt.desc()) // 최신 이미지 선택
                .fetchOne();

        // 3. 연락 방법 조회
        List<ContactMethodDTO> contactMethods = queryFactory
                .select(Projections.constructor(ContactMethodDTO.class,
                        contactMethod.contactType,
                        contactMethod.contactUrl))
                .from(contactMethod)
                .where(contactMethod.club.id.eq(clubId))
                .fetch();

        // 4. 활동 정보 조회
        String activityIntro = queryFactory
                .select(clubActivity.intro)
                .from(clubActivity)
                .where(clubActivity.club.id.eq(clubId))
                .fetchOne();

        List<String> activityImageKeyNames = Optional.ofNullable(
                queryFactory.select(clubActivityImage.keyName)
                        .from(clubActivityImage)
                        .where(clubActivityImage.clubActivity.club.id.eq(clubId))
                        .fetch()
        ).orElse(Collections.emptyList());

        ClubActivityDTO activityDTO = new ClubActivityDTO(activityIntro, activityImageKeyNames);

        // 5. 멤버 리스트 조회 (최대 5명)
        List<ClubDetailMemberDTO> leaderMembers = queryFactory
                .select(Projections.constructor(ClubDetailMemberDTO.class,
                        member.name,
                        clubMember.role.stringValue(),
                        member.occupation_of_interest,
                        club.name))
                .from(clubMember)
                .leftJoin(clubMember.member, member)
                .where(clubMember.club.id.eq(clubId)
                        .and(clubMember.role.in(ClubMemberRole.LEADER, ClubMemberRole.OFFICER)))
                .limit(5)
                .fetch();

        List<ClubDetailMemberDTO> generalMembers = queryFactory
                .select(Projections.constructor(ClubDetailMemberDTO.class,
                        member.name,
                        clubMember.role.stringValue(),
                        member.occupation_of_interest,
                        club.name))
                .from(clubMember)
                .leftJoin(clubMember.member, member)
                .where(clubMember.club.id.eq(clubId)
                        .and(clubMember.role.eq(ClubMemberRole.MEMBER)))
                .limit(5)
                .fetch();

        // 6. 콜라보 리스트 조회 (최대 2개, 최신순 정렬)
        List<ClubDetailCollabDTO> collabPosts = queryFactory
                .select(Projections.constructor(ClubDetailCollabDTO.class,
                        collabPost.title,
                        collabPost.description,
                        collabPost.openDate))
                .from(collabPost)
                .leftJoin(collabPost.clubMember, clubMember)
                .where(clubMember.club.id.eq(clubId))
                .orderBy(collabPost.createdAt.desc())
                .limit(2)
                .fetch();

        // 7. DTO 변환 후 반환
        return new ClubDetailResponseDTO(
                clubIdResult,
                clubName,
                categoryName,
                intro,
                profileImage,
                bannerImage,
                contactMethods,
                activityDTO,
                leaderMembers,
                generalMembers,
                collabPosts,
                isLeader
        );

    }




}
