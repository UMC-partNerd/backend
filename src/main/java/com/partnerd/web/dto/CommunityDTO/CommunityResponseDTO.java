package com.partnerd.web.dto.CommunityDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommunityPreviewDTO {
        Long id;
        String title;
        String content;
        List<String> communityImgKeyName;
        String profileKeyName;
        String nickName;
        String occupation_of_interest;
        String belong_to_club;
        LocalDateTime createdAt;
        int likesCnt;
        int commentCnt;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommunityPreviewListDTO {

        private List<CommunityPreviewDTO> communityList; // 채팅방 목록
        private Long nextCursor; // 다음 페이지 요청 시 사용할 커서 (null이면 마지막 페이지)
        private boolean hasNext; // 다음 페이지가 있는지 여부
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommunityTop10PreviewDTO {
        Long id;
        String title;
        String nickName;
        String profileKeyName;
        String occupation_of_interest;
        String belong_to_club;
    }

    
    // 마이페이지 - 내가 쓴 커뮤니티 모집글 모아보기 (한 칸씩)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageCommunityPreviewDTO {
        private Long communityId;
        private String title;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // 마이페이지 - 내가 쓴 커뮤니티 모집글 모아보기 (전체 리스트)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageCommunityPreviewListDTO {
        private Long memberId;
        List<MypageCommunityPreviewDTO> mypageCommunityPreviewDTOList;
    }
}
