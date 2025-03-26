package com.partnerd.repository.clubMemberRepository;

import com.partnerd.mongoRepository.domain.mapping.ClubMember;

import java.util.Optional;

public interface ClubMemberRepositoryCustom {
    Optional<ClubMember> findByClubIdAndMemberId(Long clubId, Long memberId);
}
