package com.partnerd.repository.collabAskRepository;

import com.partnerd.domain.mapping.CollabAsk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CollabAskRepository extends JpaRepository<CollabAsk, Long>, CollabAskRepositoryCustom {

    @Query("SELECT c FROM CollabAsk c WHERE c.id = :collabAskId AND c.sender.member.id = :memberId")
    Optional<CollabAsk> findByIdAndSenderMemberId(@Param("collabAskId") Long collabAskId, @Param("memberId") Long memberId);

   CollabAsk findBySender_Member_idAndCollabPost_id(Long memberId, Long collabPostId);
}
