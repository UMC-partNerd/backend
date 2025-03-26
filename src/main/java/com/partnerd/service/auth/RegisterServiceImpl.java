package com.partnerd.service.auth;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.RegisterHandler;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.AuthMemberConverter;
import com.partnerd.domain.Agreements;
import com.partnerd.domain.Member;
import com.partnerd.repository.agreementsRepository.AgreementsRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.web.dto.registerDTO.RegisterRequestDTO;
import com.partnerd.web.dto.registerDTO.RegisterResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final MemberRepository memberRepository;
    private final AgreementsRepository agreementsRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 기본 프로필 이미지 경로
    private static final String DEFAULT_PROFILE_IMAGE = "myProfileImage/basicprofile.jpg";

    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO request, String token) {
        log.debug("Register request received: {}", request);
        log.debug("Authorization token: {}", token);

        try {
            // 예외 처리: 요청 데이터와 약관 검증
            if (request == null || request.getAgreements() == null) {
                log.error("Request or Agreements data is null");
                throw new RegisterHandler(ErrorStatus.REGISTER_INVALID_REQUEST);
            }

            // JWT 토큰에서 사용자 ID 추출
            Long userId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());
            log.debug("Extracted userId from token: {}", userId);

            // 사용자 조회
            Member member = memberRepository.findById(userId)
                    .orElseThrow(() -> {
                        log.error("User not found with ID: {}", userId);
                        return new RuntimeException("User not found");
                    });
            log.debug("Fetched Member: {}", member);

            // 기존 약관 수정
            Agreements agreements = member.getAgreement();
            if (agreements == null) {
                log.error("Agreements not found for Member ID: {}", userId);
                throw new RegisterHandler(ErrorStatus.REGISTER_AGREEMENTS_NOT_FOUND);
            }
            log.debug("Fetched Agreements before update: {}", agreements);

            // 약관 데이터 업데이트
            agreements.setIs_adult(request.getAgreements().getIsAdult());
            agreements.setTerms_of_services(request.getAgreements().getTermsOfServices());
            agreements.setPersonal_info_usage(request.getAgreements().getPersonalInfoUsage());
            agreements.setOptional_info_usage(request.getAgreements().getOptionalInfoUsage());
            agreements.setMarketing_consent(request.getAgreements().getMarketingConsent());
            agreements.setMarketing_notify(request.getAgreements().getMarketingNotify());
            agreementsRepository.save(agreements);
            log.debug("Updated Agreements: {}", agreements);

            // Member 정보 업데이트 (기본 프로필 이미지 포함)
            member = AuthMemberConverter.toMemberEntity(request, member);
            if (member.getProfile_url() == null || member.getProfile_url().isEmpty()) {
                member.setProfile_url(DEFAULT_PROFILE_IMAGE);
            }

            memberRepository.save(member);
            log.debug("Updated Member saved: {}", member);

            // 응답 DTO 생성
            RegisterResponseDTO responseDTO = AuthMemberConverter.toRegisterResponseDTO(member);
            log.debug("RegisterResponseDTO created: {}", responseDTO);

            return responseDTO;

        } catch (Exception e) {
            log.error("Error in registerUser: {}", e.getMessage(), e);
            throw new RuntimeException("추가 정보 등록 중 오류가 발생했습니다.", e);
        }
    }
}
