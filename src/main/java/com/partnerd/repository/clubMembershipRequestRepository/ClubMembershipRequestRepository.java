package com.partnerd.repository.clubMembershipRequestRepository;

import com.partnerd.domain.mapping.ClubMembershipRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Optional;

public interface ClubMembershipRequestRepository extends JpaRepository<ClubMembershipRequest, Long> {
}
