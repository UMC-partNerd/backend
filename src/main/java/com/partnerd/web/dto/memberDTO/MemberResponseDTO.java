package com.partnerd.web.dto.memberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MemberResponseDTO {
    // 내프로필 수정
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateMemberResultDTO {
        Long memberId;
        LocalDateTime updatedAt;
    }
}
