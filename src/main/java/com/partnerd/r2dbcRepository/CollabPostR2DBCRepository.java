package com.partnerd.r2dbcRepository;

import com.partnerd.domain.CollabPost;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollabPostR2DBCRepository extends ReactiveCrudRepository<CollabPost, Long> {
}
