package com.partnerd.repository.clubMemberRepository;

import com.partnerd.domain.mapping.ClubMember;

import java.util.Optional;

public interface ClubMemberRepositoryCustom {
    Optional<ClubMember> findByClubIdAndMemberId(Long clubId, Long memberId);
}
