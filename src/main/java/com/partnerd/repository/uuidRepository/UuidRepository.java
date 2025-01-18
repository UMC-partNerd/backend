package com.partnerd.repository.uuidRepository;

import com.partnerd.domain.common.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
}
