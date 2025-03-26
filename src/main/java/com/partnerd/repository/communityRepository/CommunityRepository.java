package com.partnerd.repository.communityRepository;

import com.partnerd.mongoRepository.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryCustom {

    @Query("SELECT cm FROM Community cm LEFT JOIN FETCH cm.member m WHERE cm.id = :id" )
    Optional<Community> findByIdWithMemebr(@Param("id") Long communityId);
}
