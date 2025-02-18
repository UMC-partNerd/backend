package com.partnerd.web.dto.CommunityDTO;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class CommunityResponseDTO {

    @Getter
    @Builder
    public static class addResponseCommunityDTO {
        Long id;
        String title;
        String content;
        List<String> communityImgKeyName;

    }
    @Getter
    @Builder
    public static class responseLikesDTO {
        Long communityId;
        int likes;
    }

}
