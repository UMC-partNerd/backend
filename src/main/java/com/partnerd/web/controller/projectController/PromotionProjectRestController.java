package com.partnerd.web.controller.projectController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.converter.projectConverter.PromotionProjectConverter;
import com.partnerd.domain.PromotionProject;
import com.partnerd.service.projectService.PromotionProjectService;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "프로젝트 홍보 생성 API",description = "홍보할 프로젝트를 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<PromotionProjectResponseDTO.CreatePromotionProjectResultDTO> addPromotionProject(@RequestBody @Valid PromotionProjectRequestDTO.CreatePromotionProjectDTO request){

        PromotionProject promotionProject = promotionProjectService.addPromotionProject(request);
        return ApiResponse.onSuccess(PromotionProjectConverter.toCreatePromotionProjectResultDTO(promotionProject));
    }
    
    
    // 프로젝트 홍보 수정
    
    
    // 프로젝트 홍보 삭제
}
