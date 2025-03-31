package com.partnerd.r2dbcRepository;

import com.partnerd.web.dto.homeDTO.response.HomePromotionProjectDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PromotionProjectR2DBCRepositoryCustom {

    private final DatabaseClient databaseClient;

    public Flux<HomePromotionProjectDTO> findTopPromotionProjects(int limit) {
        String query = String.format("""
            SELECT pp.id, ppi.key_name AS profileImage, pp.title, pp.intro
            FROM promotion_project pp
            LEFT JOIN promotion_project_image ppi 
            ON pp.id = ppi.promotion_project_id AND ppi.image_type = 'THUMBNAIL'
            ORDER BY pp.views DESC
            LIMIT %d
            """, limit);

        return databaseClient.sql(query)
                .map((row, meta) -> new HomePromotionProjectDTO(
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
