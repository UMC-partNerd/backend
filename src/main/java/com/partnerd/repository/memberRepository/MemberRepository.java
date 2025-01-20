package com.partnerd.repository.memberRepository;

import com.partnerd.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * socialId로 사용자 정보를 조회
     * @param socialId 카카오 등 소셜 로그인 ID
     * @return Optional<Member>
     */
    Optional<Member> findBySocialId(String socialId);
}