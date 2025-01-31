package com.partnerd.web.dto.clubDTO;


import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Schema(description = "클럽 등록 요청 DTO")
public class ClubRegisterRequestDTO {

    @NotBlank(message = "동아리 이름은 비어있을 수 없습니다!")
    @Schema(description = "클럽 이름", example = "Coding Club")
    private String name;

    @Schema(description = "클럽 소개", example = "This is a club for coding enthusiasts.")
    private String intro;

    @Schema(description = "연락 방법 목록")
    private List<ContactMethodDTO> contactMethod;

    @NotNull(message = "카테고리ID는 비어있을 수 없습니다!")
    @Min(value = 1, message = "카테고리 ID는 1 이상이어야 합니다!")
    @Schema(description = "카테고리 ID", example = "2")
    private Long categoryId;

    @Schema(description = "배너 이미지 keyName")
    private String bannerKeyName;

    @Schema(description = "메인 이미지 keyName")
    private String mainKeyName;

    @Schema(description = "동아리 활동")
    private ClubActivityDTO activity;
}

