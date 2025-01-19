package com.partnerd.web.dto.oauthDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoResponseDTO {
    private Long id; // 카카오 사용자 고유 ID

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Data
    public static class KakaoAccount {
        private String email; // 이메일 추가
        private Profile profile;

        @Data
        public static class Profile {
            private String nickname; // 닉네임
        }
    }
}
