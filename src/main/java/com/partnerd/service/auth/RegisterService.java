package com.partnerd.service.auth;

import com.partnerd.web.dto.registerDTO.RegisterRequestDTO;
import com.partnerd.web.dto.registerDTO.RegisterResponseDTO;

public interface RegisterService {

    /**
     * 추가 정보 등록 로직
     * @param request 추가 정보 요청 DTO
     * @param token JWT 토큰
     * @return 저장된 사용자 응답 DTO
     */
    RegisterResponseDTO registerUser(RegisterRequestDTO request, String token);
}
