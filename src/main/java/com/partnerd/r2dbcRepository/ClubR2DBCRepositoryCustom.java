package com.partnerd.r2dbcRepository;

import com.partnerd.web.dto.homeDTO.response.HomeClubDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ClubR2DBCRepositoryCustom {

    private final DatabaseClient databaseClient;

    public Flux<HomeClubDTO> findTopClubs(int limit) {
        String query = String.format("""
                SELECT c.id, ci.key_name AS profileImage, c.name, c.intro, cat.name AS categoryName
                FROM club c
                LEFT JOIN club_image ci ON c.id = ci.club_id AND ci.image_type = 'MAIN'
                JOIN category cat ON c.category_id = cat.id
                WHERE ci.id IS NULL OR ci.image_type = 'MAIN'
                ORDER BY c.views DESC
                LIMIT %d
            """, limit);

        return databaseClient.sql(query)
                .map((row, meta) -> new HomeClubDTO(
                        row.get("id", Long.class),
                        row.get("profileImage", String.class),
                        row.get("name", String.class),
                        row.get("intro", String.class),
                        row.get("categoryName", String.class)
                ))
                .all()
                .doOnError(e -> log.error("Database error during findTopClubs", e))
                .doFinally(signal -> log.info("Query execution completed: {}", signal));
    }



}
