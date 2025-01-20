package com.partnerd.service.auth;

import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.domain.Agreements;
import com.partnerd.domain.Member;
import com.partnerd.domain.enums.SocialType;
import com.partnerd.repository.agreementsRepository.AgreementsRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.web.dto.authDTO.LoginResponseDTO;
import com.partnerd.web.dto.oauthDTO.KakaoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoOAuthServiceImpl implements OAuthService {

    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository; // MemberRepository 주입
    private final AgreementsRepository agreementsRepository; // AgreementsRepository 주입

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
        // 카카오 서버에서 액세스 토큰 가져오기
        String accessToken = getAccessToken(code);

        // 액세스 토큰으로 사용자 정보 가져오기
        KakaoResponseDTO kakaoResponse = getUserInfo(accessToken);

        // 소셜 ID와 이메일 추출
        String socialId = String.valueOf(kakaoResponse.getId());
        String email = kakaoResponse.getKakaoAccount().getEmail(); // 이메일 가져오기

        // 소셜 ID로 사용자 조회 또는 생성
        Member member = memberRepository.findBySocialId(socialId)
                .orElseGet(() -> createNewMember(socialId, email)); // 이메일 추가

        // JWT 토큰 생성
        String jwtToken = jwtTokenProvider.createToken(member.getId(), member.getNickname());

        // 응답 DTO 생성
        return LoginResponseDTO.builder()
                .jwtToken(jwtToken)
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }

    private String getAccessToken(String code) {
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

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null || !response.getBody().containsKey("access_token")) {
            throw new RuntimeException("Failed to get access token: " + response.getStatusCode());
        }

        return (String) response.getBody().get("access_token");
    }

    private KakaoResponseDTO getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<KakaoResponseDTO> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, request, KakaoResponseDTO.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to get user info: " + response.getStatusCode());
        }

        return response.getBody();
    }

    private Member createNewMember(String socialId, String email) {
        //약관 기본값 생성
        Agreements agreements = agreementsRepository.save(Agreements.builder().build());
        return memberRepository.save(Member.builder()
                .socialId(socialId)
                .socialType(SocialType.KAKAO)
                .email(email) // 소셜 로그인에서 가져온 이메일 저장
                .agreement(agreements)
                .build());
    }


}
