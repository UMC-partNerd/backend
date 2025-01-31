package com.partnerd.web.dto.clubDTO;

import com.partnerd.web.dto.personalLinkDTO.PersonalLinkRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

public class ClubRequestDTO {

    // 파트너드(동아리) 가입 요청 승인/거절
    @Getter
    @Setter
    public static class ClubJoinDTO {
        @NotNull
        private Long memberId;      // 동아리 가입 요청한 사용자 ID

        @NotNull
        private Long clubId;        // 가입 요청된 동아리 ID

        @NotNull
        private Long requestId;     // 요청 ID
    }
}
