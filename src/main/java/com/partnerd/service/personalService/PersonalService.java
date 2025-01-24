package com.partnerd.service.personalService;

import com.partnerd.domain.Personal;
import com.partnerd.web.dto.personalDTO.PersonalRequestDTO;

public interface PersonalService {
    // 퍼스널페이지 생성
    Personal addPersonal(PersonalRequestDTO.PersonalDTO request, Long memberId);

    // 퍼스널페이지 조회
    Personal readPersonal(Long memberId);

    // 퍼스널페이지 수정
    Personal updatePersonal(PersonalRequestDTO.PersonalDTO request, Long memberId);
}
