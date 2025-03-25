package com.partnerd.repository.r2dbc;

import com.partnerd.web.dto.homeDTO.response.HomeClubDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class ClubR2DBCRepositoryCustom {

    private final DatabaseClient databaseClient;

    public Flux<HomeClubDTO> findTopClubs(int limit) {
        String query = """
                SELECT c.id, ci.key_name AS keyName, c.name, c.intro, cat.name AS categoryName
                FROM club c
                LEFT JOIN club_image ci ON c.id = ci.club_id AND ci.image_type = :imageType
                JOIN category cat ON c.category_id = cat.id
                WHERE ci.id IS NULL OR ci.image_type = :imageType
                ORDER BY c.views DESC
                LIMIT :limit
                """;

        return databaseClient.sql(query)
                .bind("imageType", "MAIN")
                .bind("limit", limit)
                .map((row, meta) -> new HomeClubDTO(
                        row.get("id", Long.class),
                        row.get("keyName", String.class),
                        row.get("name", String.class),
                        row.get("intro", String.class),
                        row.get("categoryName", String.class)
                ))
                .all();
    }



}
