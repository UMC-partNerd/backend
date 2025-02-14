package com.partnerd.repository.collabPostRepository.collabPost;

import com.partnerd.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {

}
