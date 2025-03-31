package com.partnerd.r2dbcRepository;

import com.partnerd.web.dto.homeDTO.response.HomeProjectDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProjectR2DBCRepositoryCustom {

    private final DatabaseClient databaseClient;

    public Flux<HomeProjectDTO> findTopProjects(int limit) {
        String query = String.format("""
                    SELECT p.id, pi.key_name AS profileImage, p.title, p.intro
                    FROM project p
                    LEFT JOIN project_image pi ON pi.project_id = p.id AND pi.image_type = 3
                    ORDER BY p.created_at DESC
                   LIMIT %d
             """, limit);

        return databaseClient.sql(query)
                .map((row, meta) -> new HomeProjectDTO(
                        row.get("id", Long.class),
                        row.get("profileImage", String.class),
                        row.get("title", String.class),
                        row.get("intro", String.class)
                ))
                .all()
                .doOnError(e -> log.error("Database error during findTopClubs", e))
                .doFinally(signal -> log.info("Query execution completed: {}", signal));
    }

}
