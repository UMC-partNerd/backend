package com.partnerd.web.controller.projectController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.converter.projectConverter.ProjectConverter;
import com.partnerd.domain.Project;
import com.partnerd.service.projectService.ProjectService;
import com.partnerd.web.dto.projectDTO.ProjectRequestDTO;
import com.partnerd.web.dto.projectDTO.ProjectResponseDTO;
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

    // 프로젝트 모집글 생성
    @PostMapping("/recruit")
    @Operation(summary = "프로젝트 모집글 생성 API",description = "모집할 프로젝트를 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<ProjectResponseDTO.CreateProjectResultDTO> addProject(@RequestBody @Valid ProjectRequestDTO.CreateProjectDTO request){

        Project project = projectService.addProject(request);
        return ApiResponse.onSuccess(ProjectConverter.toCreateProjectResultDTO(project));
    }

    // 프로젝트 모집글 수정
    @PatchMapping("/recruit/{projectId}")
    @Operation(summary = "프로젝트 모집글 수정 API",description = "모집하는 프로젝트를 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "projectId", description = "프로젝트 모집글의 ID, path variable 입니다!")
    })
    public ApiResponse<ProjectResponseDTO.UpdateProjectResultDTO> updateProject(@PathVariable(name = "projectId") Long projectId, @RequestBody @Valid ProjectRequestDTO.UpdateProjectDTO request){

        Project project = projectService.updateProject(request, projectId);
        return ApiResponse.onSuccess(ProjectConverter.toUpdateProjectResultDTO(project));
    }
    
    // 프로젝트 모집글 삭제
    @DeleteMapping("/recruit/{projectId}")
    @Operation(summary = "프로젝트 모집글 삭제 API",description = "모집글 프로젝트를 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "projectId", description = "프로젝트 모집글의 ID, path variable 입니다!")
    })
    public ApiResponse<Void> deleteProject(@PathVariable(name = "projectId") Long projectId){

        projectService.deleteProject(projectId);
        return ApiResponse.onSuccess(null);
    }

    // 프로젝트 모집글 모아보기
    @GetMapping("/recruit")
    @Operation(summary = "프로젝트 모집글 모아보기 API",description = "모집글 프로젝트를 모아보는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<ProjectResponseDTO.ProjectPreviewListDTO> getProjectList(@RequestParam(name = "page") Integer page,
                                                                                @RequestParam(name = "status", required = false) Integer status,
                                                                                @RequestParam(name = "category", required = false) List<Long> category
                                            ){

        Page<Project> projectList = projectService.getProjectList(page - 1, status, category);
        return ApiResponse.onSuccess(ProjectConverter.projectPreviewListDTO(projectList));
    }
}
