package com.partnerd.converter;

import com.partnerd.domain.Member;
import com.partnerd.web.dto.memberDTO.MemberResponseDTO;

import java.time.LocalDateTime;

public class MemberConverter {
    // 내프로필 수정
    public static MemberResponseDTO.UpdateMemberResultDTO toUpdateMemberResultDTO(Member member){
        return MemberResponseDTO.UpdateMemberResultDTO.builder()
                .memberId(member.getId())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
