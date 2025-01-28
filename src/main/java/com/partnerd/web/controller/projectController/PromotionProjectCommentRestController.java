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
    public ApiResponse<PromotionProjectCommentResponseDTO.AddPromotionProjectCommentResultDTO> addProjectComment(@RequestHeader(value = "Authorization", required = false) @Parameter(hidden = true)  String authorizationHeader,
                                                                                                                 @PathVariable(name = "promotionProjectId") Long promotionProjectId,
                                                                                                                 @RequestBody @Valid PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request){
        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        PromotionProjectComment promotionProjectComment = promotionProjectCommentService.addPromotionProjectComment(memberId, promotionProjectId, request);
        return ApiResponse.onSuccess(PromotionProjectCommentConverter.toAddPromotionProjectCommentResultDTO(promotionProjectComment));
    }
    
}
