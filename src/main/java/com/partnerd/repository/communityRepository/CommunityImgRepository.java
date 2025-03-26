package com.partnerd.repository.communityRepository;

import com.partnerd.mongoRepository.domain.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityImgRepository extends JpaRepository<CommunityImage, Long> {
}
