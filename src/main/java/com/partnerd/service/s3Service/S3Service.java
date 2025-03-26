package com.partnerd.service.s3Service;

import com.partnerd.mongoRepository.domain.enums.ImageType;
import com.partnerd.web.dto.s3DTO.S3RequestDTO;
import com.partnerd.web.dto.s3DTO.S3ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.cloudfront.url}")
    private String cloudFrontUrl;

    private final S3Presigner s3Presigner;

    public S3ResponseDTO.S3ResponseDTOforPut getPresignedUrlForPut(S3RequestDTO.S3RequestDTOForPut s3RequestDTO) {


        String folderName = s3RequestDTO.getFolderName();
        String fileName = s3RequestDTO.getFilename();
        String type = "";


        if ( s3RequestDTO.getType() == 0 ) {
            type = String.valueOf(ImageType.BANNER);
        } else if (s3RequestDTO.getType() == 1){
            type = String.valueOf(ImageType.MAIN);
        } else if (s3RequestDTO.getType() == 3) {
            type = String.valueOf(ImageType.THUMBNAIL);
        } else if (s3RequestDTO.getType() == 4) {
            type = String.valueOf(ImageType.INTRO);
        } else if (s3RequestDTO.getType() == 5) {
            type = String.valueOf(ImageType.MYPROFILE);
        } else {
            type = String.valueOf(ImageType.EVENT);
        }


        String uniqueFileName = UUID.randomUUID() + "-" + fileName;
        String keyName = folderName + "/" + type + "/" + uniqueFileName;
        String contentType = s3RequestDTO.getContentType();

        System.out.println(contentType);

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(keyName)
                .contentType(contentType)
                .metadata(Map.of("Cache-Control", "max-age=864000")) // 캐싱 기간 10일
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(2))  // 유효시간 2시간
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
        String presignedUrl = presignedRequest.url().toString();

        s3Presigner.close();

        return S3ResponseDTO.S3ResponseDTOforPut.builder()
                .preSignedUrl(presignedUrl)
                .keyName(keyName)
                .build();

    }

    public String getCloudFrontUrl (String keyName) {

        String url = cloudFrontUrl + "/" + keyName;

        return url;
    }



}

