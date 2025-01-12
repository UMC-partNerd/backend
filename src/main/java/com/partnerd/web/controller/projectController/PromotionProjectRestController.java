package com.partnerd.web.controller.projectController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.converter.projectConverter.PromotionProjectConverter;
import com.partnerd.domain.PromotionProject;
import com.partnerd.service.projectService.PromotionProjectService;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/project")
public class PromotionProjectRestController {

    private final PromotionProjectService promotionProjectService;

    // 프로젝트 홍보 생성
    @PostMapping("/promotion")
    @Operation(summary = "프로젝트 홍보글 생성 API",description = "홍보할 프로젝트를 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<PromotionProjectResponseDTO.CreatePromotionProjectResultDTO> addPromotionProject(@RequestBody @Valid PromotionProjectRequestDTO.CreatePromotionProjectDTO request){

        PromotionProject promotionProject = promotionProjectService.addPromotionProject(request);
        return ApiResponse.onSuccess(PromotionProjectConverter.toCreatePromotionProjectResultDTO(promotionProject));
    }
    
    
    // 프로젝트 홍보 수정
    @PatchMapping("/promotion/{promotionProjectId}")
    @Operation(summary = "프로젝트 홍보글 수정 API",description = "홍보할 프로젝트를 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "promotionProjectId", description = "프로젝트 홍보글의 ID, path variable 입니다!")
    })
    public ApiResponse<PromotionProjectResponseDTO.UpdatePromotionProjectResultDTO> updatePromotionProject(@PathVariable(name = "promotionProjectId") Long promotionProjectId, @RequestBody @Valid PromotionProjectRequestDTO.UpdatePromotionProjectDTO request){

        PromotionProject promotionProject = promotionProjectService.updatePromotionProject(request, promotionProjectId);
        return ApiResponse.onSuccess(PromotionProjectConverter.toUpdatePromotionProjectResultDTO(promotionProject));
    }
    
    // 프로젝트 홍보 삭제
    @DeleteMapping("/promotion/{promotionProjectId}")
    @Operation(summary = "프로젝트 홍보글 삭제 API",description = "홍보할 프로젝트를 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "promotionProjectId", description = "프로젝트 홍보글의 ID, path variable 입니다!")
    })
    public ApiResponse<String> deletePromotionProject(@PathVariable(name = "promotionProjectId") Long promotionProjectId){

        promotionProjectService.deletePromotionProject(promotionProjectId);
        return ApiResponse.onSuccess("");
    }
}
