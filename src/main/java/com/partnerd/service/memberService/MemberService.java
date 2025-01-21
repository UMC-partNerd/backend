package com.partnerd.service.memberService;

import com.partnerd.domain.Member;
import com.partnerd.web.dto.memberDTO.MemberRequestDTO;

public interface MemberService {
    // 내프로필 조회
    Member readMember(Long memberId);

    // 내프로필 수정
    Member updateMember(MemberRequestDTO.UpdateMemberDTO request, Long memberId);

    // 닉네임 중복 확인
    boolean isNicknameDuplicate(String nickname);
}