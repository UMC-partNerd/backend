package com.partnerd.service.memberService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.domain.Member;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.web.dto.memberDTO.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    // 내프로필 수정
    @Override
    public Member readMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MYPAGE_PROFILE_NOT_FOUND));
    }

    // 내프로필 수정
    @Override
    @Transactional
    public Member updateMember(MemberRequestDTO.UpdateMemberDTO request, Long memberId) {

        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MYPAGE_PROFILE_NOT_FOUND));

        existingMember.setProfile_url(request.getProfile_url());
        existingMember.setName(request.getName());
        existingMember.setNickname(request.getNickname());
        existingMember.setBirth(request.getBirth());
        existingMember.setEmail(request.getEmail());
        existingMember.setPassword(request.getPassword());
        existingMember.setBelong_to_club(request.getBelong_to_club());
        existingMember.setOccupation_of_interest(request.getOccupation_of_interest());

        // Agreements 엔티티 업데이트
        if (existingMember.getAgreement() != null) {
            existingMember.getAgreement().setMarketing_notify(request.getMarketing_notify());
        } else {
            throw new MemberHandler(ErrorStatus.AGREEMENT_NOT_FOUND);
        }

        return memberRepository.save(existingMember);
    }

    // 닉네임 중복 확인
    @Override
    public boolean isNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
