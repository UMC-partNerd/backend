package com.partnerd.repository.memberRepository;

import com.partnerd.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>,MemberRepositoryCustom {

    /**
     * socialId로 사용자 정보를 조회
     * @param socialId 카카오 등 소셜 로그인 ID
     * @return Optional<Member>
     */
    Optional<Member> findBySocialId(String socialId);

    /**
     * 닉네임 중복 여부 확인
     *
     * @param nickname 중복 확인할 닉네임
     * @return 중복 여부
     */
    boolean existsByNickname(String nickname);

    /**
     * 컨택트 하고 싶은 사용자의 닉네임으로 해당 사용자 정보 조회
     *
     * @param nickname 컨택트 하고 싶은 사용자의 닉네임
     * @return Optional<Member>
     */
    Optional<Member> findByNickname(String nickname);




}