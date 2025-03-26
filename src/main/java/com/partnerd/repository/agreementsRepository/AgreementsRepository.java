package com.partnerd.repository.agreementsRepository;

import com.partnerd.mongoRepository.domain.Agreements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementsRepository extends JpaRepository<Agreements, Long> {
}
