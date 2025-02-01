package com.partnerd.service.auth;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.AuthHandler;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.domain.Agreements;
import com.partnerd.domain.Member;
import com.partnerd.domain.enums.SocialType;
import com.partnerd.repository.agreementsRepository.AgreementsRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.service.token.TokenService;
import com.partnerd.web.dto.authDTO.LoginResponseDTO;
import com.partnerd.web.dto.oauthDTO.KakaoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoOAuthServiceImpl implements OAuthService {

    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final AgreementsRepository agreementsRepository;
    private final TokenService tokenService; // TokenService 추가 (Redis 관련 로직 처리)

    @Value("${security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    @Value("${security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    @Value("${security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Override
    public LoginResponseDTO login(String code) {
        // 카카오 서버에서 Access Token 및 Refresh Token 가져오기
        Map<String, String> tokens = getTokens(code);
        String accessToken = tokens.get("access_token");
        String refreshToken = tokens.get("refresh_token");

        // Access Token으로 사용자 정보 가져오기
        KakaoResponseDTO kakaoResponse = getUserInfo(accessToken);

        // 소셜 ID와 이메일 추출
        String socialId = String.valueOf(kakaoResponse.getId());
        String email = kakaoResponse.getKakaoAccount().getEmail();

        // 소셜 ID로 기존 사용자 조회
        Member member = memberRepository.findBySocialId(socialId).orElse(null);
        boolean isNewUser = false; // 기본값: 기존 회원

        if (member == null) {
            // 신규 회원 생성
            member = createNewMember(socialId, email);
            isNewUser = true; // 신규 가입자로 설정
        }

        // Refresh Token을 Redis에 저장
        tokenService.saveRefreshToken(refreshToken, member.getId());

        // JWT 토큰 생성
        String jwtToken = jwtTokenProvider.createToken(member.getId(), member.getNickname());


        // 응답 DTO 생성
        // 로그인 응답 반환
        return LoginResponseDTO.builder()
                .jwtToken(jwtToken)
                .id(member.getId())
                .email(member.getEmail())
                .isNewUser(isNewUser) // 신규 가입 여부 추가
                .build();
    }

    private Map<String, String> getTokens(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, request, Map.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null
                || !response.getBody().containsKey("access_token")
                || !response.getBody().containsKey("refresh_token")) {
            throw new AuthHandler(ErrorStatus.AUTH_FAILED_TOKEN_RETRIEVAL);
        }

        return Map.of(
                "access_token", (String) response.getBody().get("access_token"),
                "refresh_token", (String) response.getBody().get("refresh_token")
        );
    }

    private KakaoResponseDTO getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<KakaoResponseDTO> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, request, KakaoResponseDTO.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new AuthHandler(ErrorStatus.AUTH_FAILED_USER_INFO);
        }

        return response.getBody();
    }

    private Member createNewMember(String socialId, String email) {
        // 약관 기본값 생성
        Agreements agreements = agreementsRepository.save(Agreements.builder().build());
        return memberRepository.save(Member.builder()
                .socialId(socialId)
                .socialType(SocialType.KAKAO)
                .email(email)
                .agreement(agreements)
                .build());
    }
}
