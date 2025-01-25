package com.partnerd.repository.clubMemberRepository;

import com.partnerd.domain.mapping.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Optional<ClubMember> findClubMemberByClubIdAndMemberId(Long clubId, Long memberId);
    ClubMember findByMember_id(Long memberId);
}
