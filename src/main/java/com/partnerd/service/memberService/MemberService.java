package com.partnerd.service.memberService;

import com.partnerd.domain.Member;
import com.partnerd.web.dto.memberDTO.MemberRequestDTO;

public interface MemberService {
    // 내프로필 수정
    Member updateMember(MemberRequestDTO.UpdateMemberDTO request, Long memberId);
}