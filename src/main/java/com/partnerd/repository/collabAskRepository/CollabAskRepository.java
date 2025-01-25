package com.partnerd.repository.collabAskRepository;

import com.partnerd.domain.mapping.CollabAsk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollabAskRepository extends JpaRepository<CollabAsk, Long>, CollabAskRepositoryCustom {



}
