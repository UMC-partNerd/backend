package com.partnerd.r2dbc;

import com.partnerd.domain.Project;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectR2DBCRepository extends ReactiveCrudRepository<Project, Long> {
}
