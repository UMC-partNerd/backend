package com.partnerd.repository.personalRepository;

import com.partnerd.mongoRepository.domain.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
    Optional<Personal> findByMemberId(Long memberId);
}
