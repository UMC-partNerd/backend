package com.partnerd.web.dto.collabDTO.response;

import com.partnerd.web.dto.categoryDTO.CategoryDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CollabPostResponseDTO {

    @Getter
    @Setter
    @Builder
    public static class addCollabPostResultDTO {
        private Long collabPostId;
        private LocalDateTime createdAt;
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
        private List<CategoryDTO> categoryDTOList;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollabPostPreviewListDTO {
        List<CollabPostPreviewDTO> collabPostPreviewDTOLList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;

    }


}
