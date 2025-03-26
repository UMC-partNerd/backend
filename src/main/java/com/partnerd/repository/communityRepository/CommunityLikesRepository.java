package com.partnerd.repository.communityRepository;

import com.partnerd.mongoRepository.domain.mapping.CommunityLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityLikesRepository extends JpaRepository<CommunityLikes, Long>, CommunityLikesRepositoryCustom {

   @Query("SELECT cl FROM CommunityLikes cl WHERE cl.community.id = :communityId AND cl.member.id = :memberId")
   CommunityLikes findByCommunityAndMember(@Param("communityId") Long communityId, @Param("memberId") Long memberId);


}
