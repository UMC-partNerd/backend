package com.partnerd.repository.communityRepository;

import com.partnerd.mongoRepository.domain.Community;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommunityRepositoryCustom{

    List<Community> findByCursorPagingWithMember(Long cursorId, Pageable pageable);
    List<Community> findTop10ByLikes();

    // 마이페이지 - 내가 쓴 커뮤니티 모아보기
    List<Community> findCommunitiesByMemberId(Long memberId);
}
