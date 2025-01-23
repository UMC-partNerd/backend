package com.partnerd.web.dto.s3DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class S3RequestDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class S3RequestDTOForPut {
        private String folderName;
        private Integer type;
        private String filename;
        private String contentType;

    }


}
