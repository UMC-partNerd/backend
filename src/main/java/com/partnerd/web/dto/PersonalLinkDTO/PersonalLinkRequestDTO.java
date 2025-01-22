package com.partnerd.web.dto.personalLinkDTO;

import lombok.Getter;
import lombok.Setter;

public class PersonalLinkRequestDTO {

    // 퍼스널페이지 생성
    @Getter
    @Setter
    public static class PersonalLinkDTO {
        String linkUrl;
    }
}
