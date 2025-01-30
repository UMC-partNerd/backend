package com.partnerd.repository.clubMembershipRequestRepository;

import com.partnerd.domain.mapping.ClubMembershipRequest;

import java.util.Optional;

public interface ClubMembershipRequestRepositoryCustom {
    Optional<ClubMembershipRequest> findByRequest(Long memberId, Long clubId, Long requestId);
    Optional<ClubMembershipRequest> findByMemberIdAndClubId(Long memberId, Long clubId);
}
