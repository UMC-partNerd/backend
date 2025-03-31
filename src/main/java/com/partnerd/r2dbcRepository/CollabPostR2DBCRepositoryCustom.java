package com.partnerd.r2dbcRepository;

import com.partnerd.web.dto.homeDTO.response.HomeCollabPostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CollabPostR2DBCRepositoryCustom {

    private final DatabaseClient databaseClient;

    public Flux<HomeCollabPostDTO> findTopCollabPosts(int limit) {
        String query = String.format("""
                    SELECT cp.id, cp.title, cp.intro, c.name AS clubName, ci.key_name AS profileImage
                    FROM collab_post  cp
                    LEFT JOIN club_member cm ON cp.club_member_id = cm.id
                    LEFT JOIN club c ON cm.club_id = c.id
                    LEFT JOIN club_image ci ON c.id = ci.club_id AND ci.image_type = 'MAIN'
                    ORDER BY cp.created_at DESC
                    LIMIT %d
            """, limit);

        return databaseClient.sql(query)
                .map((row, meta) -> new HomeCollabPostDTO(
                        row.get("id", Long.class),
                        row.get("title", String.class),
                        row.get("intro", String.class),
                        row.get("clubName", String.class),
                        row.get("profileImage", String.class)
                ))
                .all()
                .doOnError(e -> log.error("Database error during findTopClubs", e))
                .doFinally(signal -> log.info("Query execution completed: {}", signal));
    }

}
