package com.partnerd.repository.communityRepository;

import com.partnerd.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommnunityRepository extends JpaRepository<Community, Long> {

    @Query("SELECT cm FROM Community cm JOIN FETCH cm.member m WHERE cm.id = :id" )
    Optional<Community> findByIdWithMemebr(@Param("id") Long communityId);

}
