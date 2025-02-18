package com.partnerd.web.dto.CommunityDTO;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class CommunityRequestDTO {

    @Getter
    @Builder
    public static class addRequestCommunityDTO {
        String title;
        String content;
        List<String> communityImgKeyName;

    }
}
