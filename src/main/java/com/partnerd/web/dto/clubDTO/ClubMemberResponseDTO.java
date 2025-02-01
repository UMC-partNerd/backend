package com.partnerd.web.dto.clubDTO;

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
}
