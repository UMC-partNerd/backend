package com.partnerd.web.dto.personalDTO;

import com.partnerd.web.dto.personalLinkDTO.PersonalLinkResponseDTO;
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
        List<PersonalLinkResponseDTO.PersonalLinkResultDTO> personalLinkList; // 링크 목록
        LocalDateTime createdAt;
    }

    // 퍼스널페이지 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadPersonalResultDTO {
        Long personalId; // 퍼스널 ID
        String nickname; // 닉네임
        String profileKeyName;  // 사용자 프로필 keyName
        String occupation_of_interest; // 관심직군
        String belong_to_club; // 소속
        String intro; // 한줄소개
        String personalHistory; // 경력
        String education; // 학력
        String activityProject; // 활동 프로젝트
        String skill; // 스킬
        List<PersonalLinkResponseDTO.PersonalLinkResultDTO> personalLinkList; // 링크 목록
    }

    // 퍼스널페이지 수정
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePersonalResultDTO {
        Long personalId; // 퍼스널 ID
        String intro; // 한줄소개
        String personalHistory; // 경력
        String education; // 학력
        String activityProject; // 활동 프로젝트
        String skill; // 스킬
        List<PersonalLinkResponseDTO.PersonalLinkResultDTO> personalLinkList; // 링크 목록
        LocalDateTime updateAt;
    }
}
