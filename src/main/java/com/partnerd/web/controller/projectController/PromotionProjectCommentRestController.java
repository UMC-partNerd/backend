package com.partnerd.web.controller.projectController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.projectConverter.PromotionProjectCommentConverter;
import com.partnerd.domain.PromotionProjectComment;
import com.partnerd.service.projectService.PromotionProjectCommentService;
import com.partnerd.web.dto.projectDTO.PromotionProjectCommentRequestDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectCommentResponseDTO;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class PromotionProjectCommentRestController {

    private final PromotionProjectCommentService promotionProjectCommentService;
    private final JwtTokenProvider jwtTokenProvider;

    // 프로젝트 홍보글 작성
    @PostMapping("/promotion/{promotionProjectId}/comment")
    @Operation(summary = "프로젝트 홍보글 댓글 생성 API",description = "홍보하는 프로젝트에 댓글을 작성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<PromotionProjectCommentResponseDTO.AddPromotionProjectCommentResultDTO> addPromotionProjectComment(@RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
                                                                                                                 @PathVariable(name = "promotionProjectId") Long promotionProjectId,
                                                                                                                 @RequestBody @Valid PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request){
        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        PromotionProjectComment promotionProjectComment = promotionProjectCommentService.addPromotionProjectComment(memberId, promotionProjectId, request);
        return ApiResponse.onSuccess(PromotionProjectCommentConverter.toAddPromotionProjectCommentResultDTO(promotionProjectComment));
    }

    // 프로젝트 홍보글 대댓글 작성
    @PostMapping("/promotion/{promotionProjectId}/{parentId}/comment")
    @Operation(summary = "프로젝트 홍보글 대댓글 생성 API",description = "홍보하는 프로젝트의 댓글에 대댓글을 작성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<PromotionProjectCommentResponseDTO.AddPromotionProjectCommentResultDTO> addChildPromotionProjectComment(@RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
                                                                                                                      @PathVariable(name = "promotionProjectId") Long promotionProjectId,
                                                                                                                      @PathVariable(name = "parentId") Long parentId,
                                                                                                                      @RequestBody @Valid PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request){
        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        PromotionProjectComment promotionProjectComment = promotionProjectCommentService.addChildPromotionProjectComment(memberId, promotionProjectId, parentId, request);
        return ApiResponse.onSuccess(PromotionProjectCommentConverter.toAddPromotionProjectCommentResultDTO(promotionProjectComment));
    }

    // 프로젝트 홍보글 댓글/대댓글 수정
    @PatchMapping("/promotion/comment/{commentId}")
    @Operation(summary = "프로젝트 홍보글 댓글/대댓글 수정 API",description = "홍보하는 프로젝트에 댓글/대댓글을 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<PromotionProjectCommentResponseDTO.AddPromotionProjectCommentResultDTO> updatePromotionProjectComment(@RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
                                                                                                                    @PathVariable(name = "commentId") Long commentId,
                                                                                                                    @RequestBody @Valid PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request){
        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        PromotionProjectComment promotionProjectComment = promotionProjectCommentService.updatePromotionProjectComment(memberId, commentId, request);
        return ApiResponse.onSuccess(PromotionProjectCommentConverter.toAddPromotionProjectCommentResultDTO(promotionProjectComment));
    }

    // 프로젝트 홍보글 댓글/대댓글 삭제
    @DeleteMapping("/promotion/comment/{commentId}")
    @Operation(summary = "프로젝트 홍보글 댓글/대댓글 삭제 API",description = "홍보하는 프로젝트에 댓글/대댓글을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<Void> deletePromotionProjectComment(@RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
                                                                                                                             @PathVariable(name = "commentId") Long commentId){
        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        promotionProjectCommentService.deletePromotionProjectComment(memberId, commentId);
        return ApiResponse.onSuccess(null);
    }

    // 프로젝트 홍보글 댓글 전체 조회
    @GetMapping("/promotion/{promotionProjectId}/comment")
    @Operation(summary = "프로젝트 홍보글 댓글/대댓글 전체 조회 API",description = "홍보하는 프로젝트 하단에 있는 댓글 전체를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<List<PromotionProjectCommentResponseDTO.GetPromotionProjectCommentListResultDTO>> getPromotionProjectCommentList(@PathVariable(name = "promotionProjectId") Long promotionProjectId){

        List<PromotionProjectCommentResponseDTO.GetPromotionProjectCommentListResultDTO> promotionProjectCommentList = promotionProjectCommentService.getPromotionProjectCommentList(promotionProjectId);
        return ApiResponse.onSuccess(promotionProjectCommentList);
    }
}
