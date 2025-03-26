package com.partnerd.r2dbcRepository;

import com.partnerd.domain.Club;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubR2DBCRepository extends ReactiveCrudRepository<Club, Long> {
}
