package com.partnerd.web.dto.clubDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ClubResponseDTO {
    // 파트너드 목록 조회(마이페이지-한 칸씩)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubPreviewDTO {
        private Long clubId;
        private String profile;
        private String category;
        private String name;
        private String intro;
    }

    // 파트너드 목록 조회(마이페이지)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadClubPreviewListDTO {
        private List<ClubPreviewDTO> clubPreviewDTOList;
    }

    // 파트너드(동아리) 가입 요청 승인/거절
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubJoinResultDTO {
        private Long memberId;      // 동아리 가입 요청한 사용자 ID
        private Long clubId;        // 가입 요청된 동아리 ID
        private Long requestId;     // 요청 ID
        private String status;      // 가입 요청 승인/거절 결과(상태)
        private LocalDateTime updatedAt;   // 가입 요청 상태 변경 날짜
    }

    // 파트너드(동아리) 가입 요청
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClubJoinRequestResultDTO {
        private Long memberId;      // 동아리 가입 요청한 사용자 ID
        private Long clubId;        // 가입 요청된 동아리 ID
        private Long requestId;     // 요청 ID
        private String status;      // 가입 요청 상태
        private LocalDateTime createdAt;   // 가입 요청 날짜
    }
}
