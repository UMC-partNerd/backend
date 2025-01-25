package com.partnerd.web.dto.s3DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



public class S3ResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class S3ResponseDTOforPut {
        private String preSignedUrl;
        private String keyName;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class S3ResponseDTOforGet {
        private String cloudFrontUrl;
        private String keyName;

    }
}
