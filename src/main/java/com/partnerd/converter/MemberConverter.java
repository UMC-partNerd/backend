package com.partnerd.converter;

import com.partnerd.domain.Member;
import com.partnerd.web.dto.memberDTO.MemberResponseDTO;

import java.time.LocalDateTime;

public class MemberConverter {
    // 내프로필 조회
    public static MemberResponseDTO.ReadMemberResultDTO toReadMemberResultDTO(Member member){
        return MemberResponseDTO.ReadMemberResultDTO.builder()
                .profileKeyName(member.getProfile_url())
                .name(member.getName())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .birth(member.getBirth())
                .email(member.getEmail())
                .belong_to_club(member.getBelong_to_club())
                .occupation_of_interest(member.getOccupation_of_interest())
                .marketing_notify(member.getAgreement().getMarketing_notify())
                .build();
    }

    // 내프로필 수정
    public static MemberResponseDTO.UpdateMemberResultDTO toUpdateMemberResultDTO(Member member){
        return MemberResponseDTO.UpdateMemberResultDTO.builder()
                .memberId(member.getId())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
