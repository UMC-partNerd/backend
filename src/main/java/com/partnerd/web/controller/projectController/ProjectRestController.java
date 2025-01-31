package com.partnerd.web.controller.projectController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ProjectHandler;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.projectConverter.ProjectConverter;
import com.partnerd.domain.Project;
import com.partnerd.service.projectService.ProjectService;
import com.partnerd.web.dto.projectDTO.ProjectRequestDTO;
import com.partnerd.web.dto.projectDTO.ProjectResponseDTO;
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
public class ProjectRestController {

    private final ProjectService projectService;
    private final JwtTokenProvider jwtTokenProvider;

    // 프로젝트 모집글 생성
    @PostMapping("/recruit")
    @Operation(summary = "프로젝트 모집글 생성 API",description = "모집할 프로젝트를 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<ProjectResponseDTO.CreateProjectResultDTO> addProject(
            @RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
            @RequestBody @Valid ProjectRequestDTO.CreateProjectDTO request){

        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new ProjectHandler(ErrorStatus.TOKEN_EXPIRED);

        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        if (request.getThumbnailKeyName() == null){
            throw new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_BAD_REQUEST);
        }

        Project project = projectService.addProject(memberId, request);
        return ApiResponse.onSuccess(ProjectConverter.toCreateProjectResultDTO(project));
    }

    // 프로젝트 모집글 수정
    @PatchMapping("/recruit/{recruitProjectId}")
    @Operation(summary = "프로젝트 모집글 수정 API",description = "모집하는 프로젝트를 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "recruitProjectId", description = "프로젝트 모집글의 ID, path variable 입니다!")
    })
    public ApiResponse<ProjectResponseDTO.UpdateProjectResultDTO> updateProject(
            @RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
            @PathVariable(name = "recruitProjectId") Long recruitProjectId, @RequestBody @Valid ProjectRequestDTO.UpdateProjectDTO request){

        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new ProjectHandler(ErrorStatus.TOKEN_EXPIRED);

        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        Project project = projectService.updateProject(memberId, request, recruitProjectId);
        return ApiResponse.onSuccess(ProjectConverter.toUpdateProjectResultDTO(project));
    }
    
    // 프로젝트 모집글 삭제
    @DeleteMapping("/recruit/{recruitProjectId}")
    @Operation(summary = "프로젝트 모집글 삭제 API",description = "모집글 프로젝트를 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "recruitProjectId", description = "프로젝트 모집글의 ID, path variable 입니다!")
    })
    public ApiResponse<Void> deleteProject(
            @RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
            @PathVariable(name = "recruitProjectId") Long recruitProjectId){

        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new ProjectHandler(ErrorStatus.TOKEN_EXPIRED);

        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        projectService.deleteProject(memberId, recruitProjectId);
        return ApiResponse.onSuccess(null);
    }

    // 프로젝트 모집글 모아보기
    @GetMapping("/recruit")
    @Operation(summary = "프로젝트 모집글 모아보기 API",description = "모집글 프로젝트를 모아보는 API입니다. page는 1부터 시작합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", required = true),
            @Parameter(name = "status", description = "모집 상태 (0 = 모집중, 1 = 모집완료, 없으면 전체)"),
            @Parameter(name = "category", description = "카테고리 (복수 선택 가능, 1 = Web, 2 = Server, 3 = iOS, 4 = Android, 5 = Design, 6 = PM, 7 = AI/데이터, 8 = 게임 개발, 9 = 기타, 없으면 전체)"),
            @Parameter(name = "keyword", description = "검색 키워드")
    })
    public ApiResponse<ProjectResponseDTO.ProjectPreviewListDTO> getProjectList(@RequestParam(name = "page") Integer page,
                                                                                @RequestParam(name = "status", required = false) Integer status,
                                                                                @RequestParam(name = "category", required = false) List<Long> category,
                                                                                @RequestParam(name = "keyword", required = false) String keyword){

        Page<Project> projectList = projectService.getProjectList(page - 1, status, category, keyword);
        return ApiResponse.onSuccess(ProjectConverter.projectPreviewListDTO(projectList));
    }
    
    // 프로젝트 모집글 상세페이지 조회
    @GetMapping("/recruit/{recruitProjectId}")
    @Operation(summary = "프로젝트 모집글 상세페이지 조회 API",description = "모집글 프로젝트를 상세 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "recruitProjectId", description = "프로젝트 모집글의 ID, path variable 입니다!")
    })
    public ApiResponse<ProjectResponseDTO.ProjectDetailDTO> getProject(@PathVariable(name = "recruitProjectId") Long recruitProjectId){

        Project project = projectService.getProject(recruitProjectId);
        return ApiResponse.onSuccess(ProjectConverter.toProjectDetailDTO(project));
    }

    // 마이페이지 - 내가 쓴 프로젝트 모집글 모아보기
    @GetMapping("/recruit/mypage")
    @Operation(summary = "마이페이지 내가 쓴 프로젝트 모집글 목록 조회 API",description = "마이페이지의 내가 쓴 글 페이지에서 프로젝트 모집글 목록을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<ProjectResponseDTO.MypageProjectPreviewListDTO> getMyProjects(@RequestHeader("Authorization") String authorizationHeader){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        List<Project> projects = projectService.getMyProjects(memberId);

        return ApiResponse.onSuccess(ProjectConverter.toMyProjectsDTO(memberId, projects));
    }
}
