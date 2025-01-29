package com.partnerd.web.controller.projectController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ProjectHandler;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.projectConverter.PromotionProjectConverter;
import com.partnerd.domain.PromotionProject;
import com.partnerd.service.projectService.PromotionProjectService;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectResponseDTO;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/project")
public class PromotionProjectRestController {

    private final PromotionProjectService promotionProjectService;
    private final JwtTokenProvider jwtTokenProvider;

    // 프로젝트 홍보 생성
    @PostMapping("/promotion")
    @Operation(summary = "프로젝트 홍보글 생성 API",description = "홍보할 프로젝트를 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<PromotionProjectResponseDTO.CreatePromotionProjectResultDTO> addPromotionProject(
            @RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
            @RequestBody @Valid PromotionProjectRequestDTO.CreatePromotionProjectDTO request){

        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new ProjectHandler(ErrorStatus.TOKEN_EXPIRED);

        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        PromotionProject promotionProject = promotionProjectService.addPromotionProject(memberId, request);
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
    public ApiResponse<PromotionProjectResponseDTO.UpdatePromotionProjectResultDTO> updatePromotionProject(
            @RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
            @PathVariable(name = "promotionProjectId") Long promotionProjectId, @RequestBody @Valid PromotionProjectRequestDTO.UpdatePromotionProjectDTO request){

        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new ProjectHandler(ErrorStatus.TOKEN_EXPIRED);

        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        PromotionProject promotionProject = promotionProjectService.updatePromotionProject(memberId, request, promotionProjectId);
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
    public ApiResponse<Void> deletePromotionProject(
            @RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
            @PathVariable(name = "promotionProjectId") Long promotionProjectId){

        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new ProjectHandler(ErrorStatus.TOKEN_EXPIRED);

        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        promotionProjectService.deletePromotionProject(memberId, promotionProjectId);
        return ApiResponse.onSuccess(null);
    }

    // 프로젝트 홍보 모아보기 (인기순 / 최신순)
    @GetMapping("/promotion")
    @Operation(summary = "프로젝트 홍보글 모아보기 (인기순/최신순) API",description = "홍보할 프로젝트를 모아보는 API입니다. page는 1부터 시작합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", required = true),
            @Parameter(name = "sort", description = "나열 순서 (0 = 인기순, 1 = 최신순)", required = true),
    })
    public ApiResponse<PromotionProjectResponseDTO.PromotionProjectPreviewListDTO> getPromotionProjectList(@RequestParam(name = "page") Integer page,
                                                                                                           @RequestParam(name = "sort") Integer sort){

        Page<PromotionProject> promotionProjectPage = promotionProjectService.getPromotionProjectList(page - 1, sort);
        return ApiResponse.onSuccess(PromotionProjectConverter.promotionProjectPreviewListDTO(promotionProjectPage));
    }

    // 프로젝트 홍보 모아보기 (인기 top3)
    @GetMapping("/promotion/top3")
    @Operation(summary = "프로젝트 홍보글 모아보기 (인기 top3 보기) API",description = "홍보할 프로젝트를 모아보는 페이지의 인기 top3 홍보글 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<List<PromotionProjectResponseDTO.PromotionProjectPreviewDTO>> getPromotionProjectList(){

        List<PromotionProject> promotionProjectList = promotionProjectService.getPromotionProjectTop3();
        return ApiResponse.onSuccess(PromotionProjectConverter.projectPreviewDTOList(promotionProjectList));
    }

    // 프로젝트 홍보 모아보기 (검색)
    @GetMapping("/promotion/search")
    @Operation(summary = "프로젝트 홍보글 검색하기 API",description = "홍보할 프로젝트를 검색하는 API입니다. page는 1부터 시작합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<PromotionProjectResponseDTO.PromotionProjectPreviewListDTO> getPromotionProjectList(@RequestParam(name = "page") Integer page,
                                                                                                             @RequestParam(name = "keyword") String keyword){
        Page<PromotionProject> promotionProjectPage = promotionProjectService.getPromotionProjectSearchList(page - 1, keyword);
        return ApiResponse.onSuccess(PromotionProjectConverter.promotionProjectPreviewListDTO(promotionProjectPage));
    }

    // 프로젝트 홍보글 상세페이지 조회
    @GetMapping("/promotion/{promotionProjectId}")
    @Operation(summary = "프로젝트 홍보글 상세페이지 API",description = "모집할 프로젝트를 상세 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "promotionProjectId", description = "프로젝트 홍보글의 ID, path variable 입니다!")
    })
    public ApiResponse<PromotionProjectResponseDTO.PromotionProjectDetailDTO> getPromotionProject(@PathVariable(name = "promotionProjectId") Long promotionProjectId){

        PromotionProject promotionProject = promotionProjectService.getPromotionProject(promotionProjectId);
        return ApiResponse.onSuccess(PromotionProjectConverter.toPromotionProjectDetailDTO(promotionProject));
    }


    // 마이페이지 - 내가 쓴 프로젝트 홍보글 모아보기
    @GetMapping("/promotion/mypage")
    @Operation(summary = "마이페이지 내가 쓴 프로젝트 홍보글 목록 조회 API",description = "마이페이지의 내가 쓴 글 페이지에서 프로젝트 홍보글 목록을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<PromotionProjectResponseDTO.MypagePromotionProjectPreviewListDTO> getMyPromotionProjects(@RequestHeader("Authorization") String authorizationHeader){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        List<PromotionProject> promotionProjects = promotionProjectService.getMyPromotionProjects(memberId);

        return ApiResponse.onSuccess(PromotionProjectConverter.toMyPromotionProjectsDTO(memberId, promotionProjects));
    }
}
