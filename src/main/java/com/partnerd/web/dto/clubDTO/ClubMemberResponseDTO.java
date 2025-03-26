package com.partnerd.web.dto.clubDTO;

import com.partnerd.mongoRepository.domain.enums.ActiveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ClubMemberResponseDTO {
    // 파트너드(동아리) 리더 권한 위임
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubChangeLeaderDTO {
        private Long newLeaderId;
        private Long clubId;
        private LocalDateTime updatedAt;
    }

    // 파트너드(동아리) 멤버 비활성화
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubChangeActiveDTO {
        private Long memberId;
        private Long clubId;
        private ActiveType status;
        private LocalDateTime updatedAt;
    }
}
