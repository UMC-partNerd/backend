package com.partnerd;

import com.partnerd.domain.CollabPost;
import com.partnerd.domain.EventType;
import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepository;
import com.partnerd.repository.collabPostRepository.collabPost.EventTypeRepository;
import com.partnerd.repository.collabPostRepository.collabPost.bulk.CollabPostBatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback(false) // 실제 데이터 삽입 확인을 위해 롤백 방지
public class CollabPostBatchInsertTest {

    @Autowired
    private CollabPostBatchRepository collabPostBatchRepository;
    @Autowired
    private EventTypeRepository eventTypeRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate; // ✅ Batch Insert 후 데이터 개수 확인
    @Autowired
    private ClubMemberRepository clubMemberRepository; // ✅ 기존 ClubMember 조회

    @Test
    public void testBatchInsert() {
        // ✅ 기존 ClubMember ID 리스트 가져오기
        List<Long> clubMemberIds = clubMemberRepository.findAll()
                .stream()
                .map(ClubMember::getId)
                .collect(Collectors.toList());
        List<Long> eventTypeIds = eventTypeRepository.findAll()
                .stream()
                .map(EventType::getId)
                .collect(Collectors.toList());

        if (eventTypeIds.isEmpty()) {
            throw new RuntimeException("event_type 테이블에 데이터가 없습니다! 먼저 데이터를 추가하세요.");
        }

        if (clubMemberIds.isEmpty()) {
            throw new RuntimeException("ClubMember 데이터가 없습니다. 먼저 데이터를 추가하세요.");
        }

        Random random = new Random();

        // 10,000개 데이터 생성
        List<CollabPost> collabPosts = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Long randomClubMemberId = clubMemberIds.get(random.nextInt(clubMemberIds.size()));
            Long randomEventTypeId  = eventTypeIds.get(random.nextInt(eventTypeIds.size()));
            collabPosts.add(CollabPost.builder()
                            .clubMember(ClubMember.builder()
                                    .id(randomClubMemberId)
                                    .build())
                    .title("Batch Insert Post " + i)
                    .intro("Batch Insert Intro")
                    .openDate(new Date(System.currentTimeMillis()))
                    .closeDate(new java.sql.Date(System.currentTimeMillis() + (10L * 24 * 60 * 60 * 1000))) // ✅ 10일 후 날짜
                    .startDate(new java.sql.Date(System.currentTimeMillis() + (20L * 24 * 60 * 60 * 1000))) // ✅ 20일 후 날짜
                    .endDate(new java.sql.Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000))) // ✅ 30일 후 날짜
                    .collabTarget("Developers")
                    .eventMode(1)
                    .description("Batch Insert Description")
                    .eventType(EventType.builder()
                            .id(randomEventTypeId).build())
                    .build());

        }

        // ✅ Batch Insert 실행
        collabPostBatchRepository.insertAll(collabPosts);

        // ✅ 데이터 확인
        long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM collab_post", Long.class);
        System.out.println("Total records in DB: " + count);

        // ✅ 10,000개 이상 삽입 검증
        assertTrue(count >= 10000);
    }
}
