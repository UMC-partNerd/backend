package com.partnerd.repository.collabPostRepository.collabPost.bulk;

import com.partnerd.domain.CollabPost;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollabPostBatchRepository {
    private final JdbcTemplate jdbcTemplate;

    public void insertAll(List<CollabPost> collabPostList) {

        String insertSql ="INSERT INTO collab_post (club_member_id, title, intro, open_date, close_date, start_date, end_date, " +
                "collab_target, event_mode, description, event_type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CollabPost collabPost = collabPostList.get(i);
                ps.setLong(1, collabPost.getClubMember().getId());
                ps.setString(2, collabPost.getTitle());
                ps.setString(3, collabPost.getIntro());
                ps.setDate(4, (Date) collabPost.getOpenDate());
                ps.setDate(5, (Date) collabPost.getCloseDate());
                ps.setDate(6, (Date) collabPost.getStartDate());
                ps.setDate(7, (Date) collabPost.getEndDate());
                ps.setString(8, collabPost.getCollabTarget());
                ps.setInt(9, collabPost.getEventMode());
                ps.setString(10, collabPost.getDescription());
                ps.setLong(11, collabPost.getEventType().getId());
            }

            @Override
            public int getBatchSize() {
                return collabPostList.size();
            }
        });

    }
}
