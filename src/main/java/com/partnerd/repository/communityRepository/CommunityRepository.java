package com.partnerd.repository.communityRepository;

import com.partnerd.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
