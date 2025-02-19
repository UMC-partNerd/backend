package com.partnerd.repository.communityRepository;

import com.partnerd.domain.Community;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommunityRepositoryCustom{

    List<Community> findByCursorPagingWithMember(Long cursorId, Pageable pageable);
    List<Community> findTop10ByLikes();
}
