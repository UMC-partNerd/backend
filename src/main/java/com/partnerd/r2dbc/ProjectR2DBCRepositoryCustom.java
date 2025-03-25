package com.partnerd.repository.r2dbc;

import com.partnerd.web.dto.homeDTO.response.HomeProjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class ProjectR2DBCRepositoryCustom {

    private final DatabaseClient databaseClient;

    public Flux<HomeProjectDTO> findTopProjects(int limit) {
        String query = """
                    SELECT p.id, pi.keyName AS keyName, p.title, p.intro
                    FROM project p
                    LEFT JOIN project_image pi ON pi.project_id = p.id AND pi.image_type = 'THUMBNAIL'
                    ORDER BY p.created_at DESC
                    LIMIT :limit
                """;

        return databaseClient.sql(query)
                .bind("limit", limit)
                .map((row, meta) -> new HomeProjectDTO(
                        row.get("id", Long.class),
                        row.get("keyName", String.class),
                        row.get("title", String.class),
                        row.get("intro", String.class)
                ))
                .all();
    }

}
