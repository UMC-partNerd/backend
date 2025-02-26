package com.partnerd.web.dto.collabDTO.response;

import com.partnerd.domain.CollabInquiry;
import com.partnerd.domain.ContactMethod;
import com.partnerd.repository.collabPostRepository.collabPost.CollabPostRepositoryCustomImpl;
import com.partnerd.web.dto.categoryDTO.CategoryDTO;
import com.partnerd.web.dto.categoryDTO.CollabPostCategoryDTO;
import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CollabPostResponseDTO {

    @Getter
    @Setter
    @Builder
    public static class addCollabPostResultDTO {
        private Long collabPostId;
        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PagingResultDTO<T> {
        private List<T> data;       //  현재 페이지 데이터 리스트
        private int listSize;       //  현재 페이지 데이터 개수
        boolean hasMorePages;
        int availablePages;
        List<CollabPostRepositoryCustomImpl.PageReferenceDTO> pageReferenceDTOList;
        private boolean isFirst;    //  첫 페이지 여부
        private boolean isLast;     //  마지막 페이지 여부
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollabPostPreviewDTO {

        private Long collabPostId;
        private String title;
        private Date startDate;
        private Date endDate;
        @Setter
        private List<CollabPostCategoryDTO> categoryDTOList;
        private String mainImgKeyname;
        private LocalDateTime createdAt;
        @Builder
        public CollabPostPreviewDTO(Long id, String title, Date startDate, Date endDate, String mainImgKeyname) {
            this.collabPostId = id;
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
            this.mainImgKeyname = mainImgKeyname;
        }

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollabPostPreviewListDTO {
        List<CollabPostPreviewDTO> collabPostPreviewDTOList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollabPostDetailDTO {
        private String title;
        private String intro;
        private Date openDate;
        private Date closeDate;
        private Date startDate;
        private Date endDate;
        private String collabTarget;
        private int eventMode;
        private String description;
        private String eventType;
        private String nickname;
        private List<CategoryDTO> categoryDTOList;
        private List<ContactMethodDTO> contactMethod;
        private List<CollabInquiryDTO> CollabInquiryList;
        private String profileKeyname;
        private String bannerKeyName;
        private String mainKeyName;
        private List<String> eventImgKeyNameList;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageDTO {
        private String bannerImageUrl;  // 배너 이미지 URL
        private String mainImageUrl;    // 메인 이미지 URL
        private List<String> eventImageUrls; // 이벤트 이미지들의 URL 리스트
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollabInquiryDTO {
        private Long id;
        private Long parentId;
        private String nickname;
        private String contents;
        private int likes;
        private String profileImgKeyname;

        public static CollabInquiryDTO fromEntity(CollabInquiry collabInquiry) {
            Long parentId = null;
            if (collabInquiry.getParentInquiry() != null) {
                parentId = collabInquiry.getParentInquiry().getId();
            }
            return CollabInquiryDTO.builder()
                    .id(collabInquiry.getId())
                    .nickname(collabInquiry.getMember().getNickname())
                    .contents(collabInquiry.getContents())
                    .likes(collabInquiry.getLikes())
                    .profileImgKeyname(collabInquiry.getMember().getProfile_url())
                    .parentId(parentId)
                    .build();
        }

    }



    // 마이페이지 - 내가 쓴 콜라보레이션 모아보기 (한 칸씩)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageCollabPostPreviewDTO {
        private Long collabPostId;
        private String title;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // 마이페이지 - 내가 쓴 콜라보레이션 모아보기 (전체 리스트)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageCollabPostPreviewListDTO {
        private Long memberId;
        private List<CollabPostResponseDTO.MypageCollabPostPreviewDTO> mypageCollabPostPreviewDTOList;
    }

}
