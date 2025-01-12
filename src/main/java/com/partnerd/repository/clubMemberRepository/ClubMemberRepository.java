package com.partnerd.repository.clubMemberRepository;

import com.partnerd.domain.mapping.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
}
