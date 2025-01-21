package com.partnerd.web.dto.memberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

public class MemberResponseDTO {

    // 내프로필 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadMemberResultDTO {
        String profile_url;           // 프로필 사진
        String name;                 // 이름
        String nickname;             // 닉네임
        Date birth;                // 생년월일
        String email;                // 이메일
        String belong_to_club;         // 소속
        String occupation_of_interest; // 관심 직군
        Boolean marketing_notify;     // 마케팅 수신 동의 여부
    }

    // 내프로필 수정
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateMemberResultDTO {
        Long memberId;
        LocalDateTime updatedAt;
    }

    // 프로젝트 상세페이지 속 작성자 정보
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberForProjectDetailDTO {
        private Long id; // 사용자 ID
        private String name; // 이름
        private String occupation_of_interest; // 관심 직군
        private String belong_to_club; // 소속
    }
}
