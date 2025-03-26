package com.partnerd.repository.clubMembershipRequestRepository;

import com.partnerd.domain.mapping.ClubMembershipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMembershipRequestRepository extends JpaRepository<ClubMembershipRequest, Long> {
}
