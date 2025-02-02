package com.partnerd.repository.clubRepository;

import com.partnerd.domain.QCategory;
import com.partnerd.domain.QClub;
import com.partnerd.domain.QClubImage;
import com.partnerd.domain.enums.ImageType;
import com.partnerd.web.dto.clubDTO.ClubDTO;
import com.partnerd.web.dto.homeDTO.response.HomeClubDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

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


}
