package com.partnerd.repository.r2dbc;

import com.partnerd.domain.PromotionProject;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionProjectR2DBCRepository extends ReactiveCrudRepository<PromotionProject, Long> {



}
