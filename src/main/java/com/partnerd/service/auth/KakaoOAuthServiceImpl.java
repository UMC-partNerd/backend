package com.partnerd.service.auth;

import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.web.dto.auth.LoginResponse;
import com.partnerd.web.dto.oauth.KakaoResponse;
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

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Override
    public LoginResponse login(String code) {
        String accessToken = getAccessToken(code);
        KakaoResponse kakaoResponse = getUserInfo(accessToken);

        return LoginResponse.builder()
                .jwtToken(jwtTokenProvider.createToken(kakaoResponse.getId(),
                        kakaoResponse.getKakaoAccount().getProfile().getNickname()))
                .id(kakaoResponse.getId())
                .nickname(kakaoResponse.getKakaoAccount().getProfile().getNickname())
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

    private KakaoResponse getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<KakaoResponse> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, request, KakaoResponse.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to get user info: " + response.getStatusCode());
        }

        return response.getBody();
    }
}
