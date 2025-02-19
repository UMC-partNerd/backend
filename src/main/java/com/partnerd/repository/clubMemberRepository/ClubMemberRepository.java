package com.partnerd.repository.clubMemberRepository;

import com.partnerd.domain.Club;
import com.partnerd.domain.enums.ClubMemberRole;
import com.partnerd.domain.mapping.ClubMember;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Optional<ClubMember> findClubMemberByClubIdAndMemberId(Long clubId, Long memberId);

    List<ClubMember> findByMember_idAndRole(Long memberId, ClubMemberRole role);

    @Query("SELECT DISTINCT cm FROM ClubMember cm " +
            "LEFT JOIN FETCH cm.sendCollabAsks " +
            "LEFT JOIN FETCH cm.receivedCollabAsks " +
            "LEFT JOIN FETCH cm.club " +
            "WHERE cm.member.id = :memberId")
    ClubMember findByMemberIdWithClub(Long memberId);

    @Query("SELECT cm.club FROM ClubMember cm " +
            "WHERE cm.member.id = :memberId AND cm.role IN :roles")
    List<Club> findClubsByRole(@Param("memberId") Long memberId, @Param("roles") List<ClubMemberRole> roles);

    //파트너드 멤버 조회
    List<ClubMember> findByClubId(Long clubId);
}
