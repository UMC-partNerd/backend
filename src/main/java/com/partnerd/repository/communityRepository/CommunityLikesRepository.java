package com.partnerd.repository.communityRepository;

import com.partnerd.domain.mapping.CommunityLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityLikesRepository extends JpaRepository<CommunityLikes, Long>, CommunityLikesRepositoryCustom {

   CommunityLikes findByCommunity_idAndMember_id(Long communityId, Long memberId);

}
