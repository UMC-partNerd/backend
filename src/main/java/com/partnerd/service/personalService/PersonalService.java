package com.partnerd.service.personalService;

import com.partnerd.domain.Personal;
import com.partnerd.web.dto.personalDTO.PersonalRequestDTO;

public interface PersonalService {
    // 퍼스널페이지 생성
    Personal addPersonal(PersonalRequestDTO.CreatePersonalDTO request, Long memberId);

    // 퍼스널페이지 조회
    Personal readPersonal(Long memberId);
}
