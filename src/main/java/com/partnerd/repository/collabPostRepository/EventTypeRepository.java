package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {
    Optional<EventType> findById(Long id);

}
