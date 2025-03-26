package com.partnerd.r2dbcRepository;

import com.partnerd.mongoRepository.domain.PromotionProject;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionProjectR2DBCRepository extends ReactiveCrudRepository<PromotionProject, Long> {



}
