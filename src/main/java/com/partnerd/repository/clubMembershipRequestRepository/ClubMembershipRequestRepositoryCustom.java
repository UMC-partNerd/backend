package com.partnerd.repository.clubMembershipRequestRepository;

import com.partnerd.mongoRepository.domain.mapping.ClubMembershipRequest;

import java.util.List;
import java.util.Optional;

public interface ClubMembershipRequestRepositoryCustom {
    Optional<ClubMembershipRequest> findByRequest(Long memberId, Long clubId, Long requestId);
    Optional<ClubMembershipRequest> findByMemberIdAndClubId(Long memberId, Long clubId);
    List<ClubMembershipRequest> findAllPendingRequestsByClubId(Long clubId);
}
