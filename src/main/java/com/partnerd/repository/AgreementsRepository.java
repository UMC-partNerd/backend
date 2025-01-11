package com.partnerd.repository;

import com.partnerd.domain.Agreements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementsRepository extends JpaRepository<Agreements, Long> {
}
