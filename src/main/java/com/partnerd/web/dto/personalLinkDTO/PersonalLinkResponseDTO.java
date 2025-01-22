package com.partnerd.web.dto.personalLinkDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PersonalLinkResponseDTO {
    // 퍼스널페이지 생성
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePersonalLinkResultDTO {
        String linkUrl; // 링크 URL
    }
}
