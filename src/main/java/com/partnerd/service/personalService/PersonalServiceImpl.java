package com.partnerd.service.personalService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.MemberHandler;
import com.partnerd.apiPaylaod.exception.handler.PersonalHandler;
import com.partnerd.converter.PersonalConverter;
import com.partnerd.domain.Member;
import com.partnerd.domain.Personal;
import com.partnerd.domain.PersonalLink;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.personalRepository.PersonalRepository;
import com.partnerd.web.dto.personalDTO.PersonalRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonalServiceImpl  implements PersonalService {
    private final PersonalRepository personalRepository;
    private final MemberRepository memberRepository;

    // 퍼스널페이지 생성
    @Override
    @Transactional
    public Personal addPersonal(PersonalRequestDTO.PersonalDTO request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->  new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Personal personal = PersonalConverter.toPersonal(request, member);
        personal.setMember(member);

        return personalRepository.save(personal);
    }

    // 퍼스널페이지 조회
    @Override
    public Personal readPersonal(Long memberId) {
        // 전달받은 멤버 ID로 멤버 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND)); // 멤버가 없으면 오류 반환

        // 멤버 ID를 외래키로 갖는 퍼스널 데이터 조회
        Optional<Personal> personalOptional = personalRepository.findByMemberId(memberId);

        // 퍼스널 데이터가 존재하지 않는 경우 null 값으로 초기화된 퍼스널 객체 반환
        if (personalOptional.isEmpty()) {
            return Personal.builder()
                    .intro(null)
                    .personalHistory(null)
                    .education(null)
                    .activityProject(null)
                    .skill(null)
                    .member(member)
                    .personalLinkList(Collections.emptyList())
                    .build();
        }

        // 퍼스널 데이터가 존재하면 반환
        Personal personal = personalOptional.get();
        personal.setMember(member);

        return personal;
    }

    // 퍼스널페이지 수정
    @Override
    @Transactional
    public Personal updatePersonal(PersonalRequestDTO.PersonalDTO request, Long memberId) {
        // 1. 멤버 조회 (멤버가 존재하지 않으면 예외 발생)
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 2. 기존 퍼스널 페이지 데이터 조회 (퍼스널 페이지가 없으면 예외 발생)
        Personal personal = personalRepository.findByMemberId(memberId)
                .orElseThrow(() -> new PersonalHandler(ErrorStatus.PERSONAL_NOT_FOUND));

        // 3. 요청 데이터로 필드 업데이트
        if (request.getIntro() != null) personal.setIntro(request.getIntro());
        if (request.getPersonalHistory() != null) personal.setPersonalHistory(request.getPersonalHistory());
        if (request.getEducation() != null) personal.setEducation(request.getEducation());
        if (request.getActivityProject() != null) personal.setActivityProject(request.getActivityProject());
        if (request.getSkill() != null) personal.setSkill(request.getSkill());

        // 4. 퍼스널 링크 업데이트 (기존 링크 리스트를 비우고 새로 추가)
        if (request.getPersonalLinkList() != null) {
            personal.getPersonalLinkList().clear();
            request.getPersonalLinkList().forEach(linkDTO -> {
                PersonalLink link = PersonalLink.builder()
                        .linkUrl(linkDTO.getLinkUrl())
                        .build();
                personal.addLink(link);
            });
        }

        // 5. 수정된 퍼스널 데이터 저장
        return personalRepository.save(personal);
    }
}
