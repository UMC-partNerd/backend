package com.partnerd.web.dto.memberDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class MemberRequestDTO {
    // 내프로필 수정
    @Getter
    @Setter
    public static class UpdateMemberDTO {
        String profile_url;   // 프로필 사진
        String name;    // 이름
        String nickname; // 닉네임
        Date birth; // 생년월일
        String email; // 이메일
        String password ; // 비밀번호
        String belong_to_club; // 소속
        String occupation_of_interest; // 관심 직군
        Boolean marketing_notify; // 마케팅 수신 여부
    }
}
