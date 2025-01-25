package com.partnerd.web.controller;


import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.service.s3Service.S3Service;
import com.partnerd.web.dto.s3DTO.S3RequestDTO;
import com.partnerd.web.dto.s3DTO.S3ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;


    @PostMapping("/preSignedUrl")
    @Operation(summary = "PUT 메서드용 S3 preSignedUrl 요청 ", description = "PUT 메서드용 S3 preSignedUrl를 요청하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<S3ResponseDTO.S3ResponseDTOforPut> getPreSignedUrlForPut(@RequestBody S3RequestDTO.S3RequestDTOForPut s3RequestDTO) {

         return ApiResponse.onSuccess(s3Service.getPresignedUrlForPut(s3RequestDTO));

    }

    @GetMapping("/preSignedUrl")
    @Operation(summary = "이미지 조회 할 수 있는 cloudFrontUrl 요청", description = "이미지 조회 할 수 있는 cloudFrontUrl 요청 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<S3ResponseDTO.S3ResponseDTOforGet> getCloudFrontUrlForGet(@RequestParam String keyName) {

        String cloudFrontUrl = s3Service.getCloudFrontUrl(keyName);

        return ApiResponse.onSuccess(
                S3ResponseDTO.S3ResponseDTOforGet.builder()
                .cloudFrontUrl(cloudFrontUrl)
                .keyName(keyName)
                .build());

    }



}
