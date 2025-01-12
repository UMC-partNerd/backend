package com.partnerd.repository.memberRepository;

import com.partnerd.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
