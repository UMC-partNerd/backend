package com.partnerd.repository.personalLinkRepository;

import com.partnerd.mongoRepository.domain.PersonalLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalLinkRepository extends JpaRepository<PersonalLink, Long> {
}
