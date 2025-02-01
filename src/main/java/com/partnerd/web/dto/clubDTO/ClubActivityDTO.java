package com.partnerd.web.dto.clubDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "클럽 활동 등록 DTO")
public class ClubActivityDTO {

    @Schema(description = "활동 소개", example = "우리 동아리는 이런 활동에 참여했어요!")
    private String intro;

    @Schema(description = "활동 이미지 keyNames")
    private List<String> activityImageKeyNames;
}
