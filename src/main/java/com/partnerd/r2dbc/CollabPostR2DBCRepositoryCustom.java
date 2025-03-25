package com.partnerd.repository.r2dbc;

import com.partnerd.web.dto.homeDTO.response.HomeCollabPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class CollabPostR2DBCRepositoryCustom {

    private final DatabaseClient databaseClient;

    public Flux<HomeCollabPostDTO> findTopCollabPosts(int limit) {
        String query = """
                    SELECT cp.id, cp.title, cp.intro, c.name AS clubName , ci.keyName AS keyName
                    FROM collab_post  cp
                    LEFT JOIN club_memebr cm ON cp.club_member_id = cm.id
                    LEFT JOIN club c ON cm.club_id = c.id
                    LEFT JOIN club_image ci ON c.id = ci.club_id AND ci.image_type = 'MAIN'
                    ORDER BY cp.created_at DESC
                    LIMIT :limit
                """;


        return databaseClient.sql(query)
                .bind("limit", limit)
                .map((row, meta) -> new HomeCollabPostDTO(
                        row.get("id", Long.class),
                        row.get("title", String.class),
                        row.get("intro", String.class),
                        row.get("clubName", String.class),
                        row.get("keyName", String.class)
                ))
                .all();
    }

}
