package com.partnerd.web.dto.collabDTO.request;

import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CollabPostRequestDTO {

    @Getter
    @Setter
    @Builder
    public static class RequestCollabPostDTO {
        private String title;
        private String intro;
        private Date openDate;
        private Date closeDate;
        private Date startDate;
        private Date endDate;
        private String collabTarget;
        private Long eventTypeId;
        private int eventMode;
        private String description;
        private String bannerKeyName;
        private String mainKeyName;
        private List<String> eventImgKeyNameList;
        private List<ContactMethodDTO> contactMethodDTOList;
        private List<Long> categoryIds;
    }



    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestNoOffsetPagingDTO {

        @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
        private int pageNum;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime lastCreatedAt; // 최신순으로 불러올 시 -> 이전 페이지의 마지막 글의 생성일

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date lastEndDate; // 마감순으로 불러올 시 -> 이전 페이지의 마지막 글의 마감일

        @Min(value = 1, message = "ID는 1 이상이어야 합니다.")
        private Long lastId;

        @NotBlank(message = "정렬 기준(sortBy)은 필수입니다.")
        private String sortBy = "endDate";

        @Min(value = 1, message = "페이지 크기는 최소 1 이상이어야 합니다.")
        @Max(value = 100, message = "페이지 크기는 최대 100까지 가능합니다.")
        private int size;

        public RequestNoOffsetPagingDTO(String sortBy, Integer size, Integer pageNum, LocalDateTime lastCreatedAt, Date lastEndDate, Long lastId) {
            this.sortBy = (sortBy == null || sortBy.isBlank()) ? "endDate" : sortBy;
            this.size = (size == null || size < 1) ? 9 : size;
            this.pageNum = (pageNum == null || pageNum < 1) ? 1 : pageNum;

            // 첫 페이지면 lastCreatedAt, lastEndDate, lastId를 null로 설정
            if (this.pageNum == 1) {
                this.lastCreatedAt = null;
                this.lastEndDate = null;
                this.lastId = null;
            } else {
                this.lastCreatedAt = lastCreatedAt;
                this.lastEndDate = lastEndDate;
                this.lastId = lastId;
            }
        }
    }


}
