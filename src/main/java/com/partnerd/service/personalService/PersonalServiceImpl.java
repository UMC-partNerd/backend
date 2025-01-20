package com.partnerd.service.personalService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.converter.PersonalConverter;
import com.partnerd.domain.Member;
import com.partnerd.domain.Personal;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.personalRepository.PersonalRepository;
import com.partnerd.web.dto.personalDTO.PersonalRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonalServiceImpl  implements PersonalService {
    private final PersonalRepository personalRepository;
    private final MemberRepository memberRepository;

    // 퍼스널페이지 생성
    @Override
    @Transactional
    public Personal addPersonal(PersonalRequestDTO.CreatePersonalDTO request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new MemberHandler(ErrorStatus.MYPAGE_PROFILE_NOT_FOUND));

        Personal personal = PersonalConverter.toPersonal(request, member);
        personal.setMember(member);

        return personalRepository.save(personal);
    }
}
