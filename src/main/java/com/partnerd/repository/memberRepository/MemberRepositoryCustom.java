package com.partnerd.repository.memberRepository;

import com.partnerd.domain.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findByNicknameContaining(String nickname);
}
