package com.partnerd.repository.r2dbc;

import com.partnerd.web.dto.homeDTO.response.HomePromotionProjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class PromotionProjectR2DBCRepositoryCustom {

    private final DatabaseClient databaseClient;

    public Flux<HomePromotionProjectDTO> findTopPromotionProjects(int limit) {
        String query = """
                SELECT pp.id, ppi.key_name AS keyName, pp.title, pp.intro
                FROM promotion_project pp
                LEFT JOIN promotion_project_image ppi 
                ON pp.id = ppi.promotion_project_id AND ppi.image_type = 'THUMBNAIL'
                ORDER BY pp.views DESC
                LIMIT :limit
                """;

        return databaseClient.sql(query)
                .bind("limit", limit)
                .map((row, meta) -> new HomePromotionProjectDTO(
                        row.get("id", Long.class),
                        row.get("keyName", String.class),
                        row.get("title", String.class),
                        row.get("intro", String.class)
                ))
                .all();
    }

}
