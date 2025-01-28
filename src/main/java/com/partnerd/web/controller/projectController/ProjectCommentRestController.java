package com.partnerd.web.controller.projectController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.projectConverter.ProjectCommentConverter;
import com.partnerd.domain.ProjectComment;
import com.partnerd.service.projectService.ProjectCommentService;
import com.partnerd.web.dto.projectDTO.ProjectCommentRequestDTO;
import com.partnerd.web.dto.projectDTO.ProjectCommentResponseDTO;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectCommentRestController {

    private final ProjectCommentService projectCommentService;
    private final JwtTokenProvider jwtTokenProvider;

    // 모집 프로젝트 댓글 작성
    @PostMapping("/recruit/{recruitProjectId}/comment")
    @Operation(summary = "프로젝트 모집글 댓글 생성 API",description = "모집할 프로젝트에 댓글을 작성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<ProjectCommentResponseDTO.AddProjectCommentResultDTO> addProjectComment( @RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
                                                                                                @PathVariable(name = "recruitProjectId") Long recruitProjectId,
                                                                                                @RequestBody @Valid ProjectCommentRequestDTO.AddProjectCommentDTO request){
        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        ProjectComment projectComment = projectCommentService.addProjectComment(memberId, recruitProjectId, request);
        return ApiResponse.onSuccess(ProjectCommentConverter.toAddProjectCommentResultDTO(projectComment));
    }

    // 모집 프로젝트 대댓글 작성
    @PostMapping("/recruit/{recruitProjectId}/{parentId}/comment")
    @Operation(summary = "프로젝트 모집글 대댓글 생성 API",description = "모집할 프로젝트의 댓글에 대댓글을 작성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<ProjectCommentResponseDTO.AddProjectCommentResultDTO> addChildProjectComment( @RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
                                                                                                @PathVariable(name = "recruitProjectId") Long recruitProjectId,
                                                                                                @PathVariable(name = "parentId") Long parentId,
                                                                                                @RequestBody @Valid ProjectCommentRequestDTO.AddProjectCommentDTO request){
        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        ProjectComment projectComment = projectCommentService.addChildProjectComment(memberId, recruitProjectId, parentId, request);
        return ApiResponse.onSuccess(ProjectCommentConverter.toAddProjectCommentResultDTO(projectComment));
    }

    // 모집 프로젝트 댓글/대댓글 수정
    @PatchMapping("/recruit/comment/{commentId}")
    @Operation(summary = "프로젝트 모집글 댓글/대댓글 수정 API",description = "모집할 프로젝트에 댓글/대댓글을 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<ProjectCommentResponseDTO.AddProjectCommentResultDTO> updateProjectComment( @RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
                                                                                                @PathVariable(name = "commentId") Long commentId,
                                                                                                @RequestBody @Valid ProjectCommentRequestDTO.AddProjectCommentDTO request){
        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        ProjectComment projectComment = projectCommentService.updateProjectComment(memberId, commentId, request);
        return ApiResponse.onSuccess(ProjectCommentConverter.toAddProjectCommentResultDTO(projectComment));
    }

    // 모집 프로젝트 댓글/대댓글 삭제
    @DeleteMapping("/recruit/comment/{commentId}")
    @Operation(summary = "프로젝트 모집글 댓글/대댓글 삭제 API",description = "모집할 프로젝트에 댓글/대댓글을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<Void> deleteProjectComment( @RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
                                                                                                   @PathVariable(name = "commentId") Long commentId){
        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        projectCommentService.deleteProjectComment(memberId, commentId);
        return ApiResponse.onSuccess(null);
    }
}
