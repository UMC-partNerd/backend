package com.partnerd.web.dto.personalDTO;

import com.partnerd.web.dto.PersonalLinkDTO.PersonalLinkResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PersonalResponseDTO {
    // 퍼스널페이지 생성
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePersonalResultDTO {
        Long personalId; // 퍼스널 ID
        String intro; // 한줄소개
        String personalHistory; // 경력
        String education; // 학력
        String activityProject; // 활동 프로젝트
        String skill; // 스킬
        List<PersonalLinkResponseDTO.CreatePersonalLinkResultDTO> personalLinkList; // 링크 목록
        LocalDateTime createdAt;
    }
}
