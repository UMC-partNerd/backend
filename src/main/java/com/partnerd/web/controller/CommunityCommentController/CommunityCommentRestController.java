package com.partnerd.web.controller.CommunityCommentController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CommunityCommentHandler;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.CommunityCommentConverter;
import com.partnerd.domain.CommunityComment;
import com.partnerd.service.CommunityCommentService.CommunityCommentService;
import com.partnerd.web.dto.CommunityCommentDTO.CommunityCommentRequestDTO;
import com.partnerd.web.dto.CommunityCommentDTO.CommunityCommentResponseDTO;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityCommentRestController {

    private final CommunityCommentService communityCommentService;
    private final JwtTokenProvider jwtTokenProvider;

    // 커뮤니티 댓글 작성
    @PostMapping("/{communityId}/comment")
    @Operation(summary = "커뮤니티 댓글 생성 API",description = "커뮤니티의 댓글을 작성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "communityId", description = "커뮤니티의 ID, path variable 입니다!")
    })
    public ApiResponse<CommunityCommentResponseDTO.AddCommunityCommentResultDTO> addCommunityComment(@RequestHeader(value = "Authorization") @Parameter(hidden = true)  String authorizationHeader,
                                                                                               @PathVariable(name = "communityId") Long communityId,
                                                                                               @RequestBody @Valid CommunityCommentRequestDTO.AddCommunityCommentDTO request){
        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new CommunityCommentHandler(ErrorStatus.TOKEN_EXPIRED);

        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        CommunityComment communityComment = communityCommentService.addCommunityComment(memberId, communityId, request);
        return ApiResponse.onSuccess(CommunityCommentConverter.toAddCommunityCommentResultDTO(communityComment));
    }

    // 커뮤니티 대댓글 작성
    @PostMapping("/{communityId}/{parentId}/comment")
    @Operation(summary = "커뮤니티 대댓글 생성 API",description = "커뮤니티의 댓글에 대댓글을 작성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "communityId", description = "커뮤니티의 ID, path variable 입니다!"),
            @Parameter(name = "parentId", description = "대댓글을 달 부모 댓글의 ID, path variable 입니다!")
    })
    public ApiResponse<CommunityCommentResponseDTO.AddCommunityCommentResultDTO> addChildCommunityComment( @RequestHeader(value = "Authorization") @Parameter(hidden = true)  String authorizationHeader,
                                                                                                     @PathVariable(name = "communityId") Long communityId,
                                                                                                     @PathVariable(name = "parentId") Long parentId,
                                                                                                     @RequestBody @Valid CommunityCommentRequestDTO.AddCommunityCommentDTO request){
        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new CommunityCommentHandler(ErrorStatus.TOKEN_EXPIRED);

        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        CommunityComment communityComment = communityCommentService.addChildCommunityComment(memberId, communityId, parentId, request);
        return ApiResponse.onSuccess(CommunityCommentConverter.toAddCommunityCommentResultDTO(communityComment));
    }

    // 커뮤니티 댓글/대댓글 수정
    @PatchMapping("/comment/{commentId}")
    @Operation(summary = "커뮤니티 댓글/대댓글 수정 API",description = "커뮤니티의 댓글/대댓글을 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "commentId", description = "커뮤니티의 댓글 ID, path variable 입니다!")
    })
    public ApiResponse<CommunityCommentResponseDTO.AddCommunityCommentResultDTO> updateCommunityComment( @RequestHeader(value = "Authorization") @Parameter(hidden = true)  String authorizationHeader,
                                                                                                   @PathVariable(name = "commentId") Long commentId,
                                                                                                   @RequestBody @Valid CommunityCommentRequestDTO.AddCommunityCommentDTO request){
        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new CommunityCommentHandler(ErrorStatus.TOKEN_EXPIRED);

        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        CommunityComment communityComment = communityCommentService.updateCommunityComment(memberId, commentId, request);
        return ApiResponse.onSuccess(CommunityCommentConverter.toAddCommunityCommentResultDTO(communityComment));
    }

    // 커뮤니티 댓글/대댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    @Operation(summary = "커뮤니티 댓글/대댓글 삭제 API",description = "커뮤니티의 댓글/대댓글을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "commentId", description = "커뮤니티의 댓글 ID, path variable 입니다!")
    })
    public ApiResponse<Void> deleteCommunityComment( @RequestHeader(value = "Authorization") @Parameter(hidden = true)  String authorizationHeader,
                                                   @PathVariable(name = "commentId") Long commentId){
        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new CommunityCommentHandler(ErrorStatus.TOKEN_EXPIRED);

        // jwt토큰으로 멤버id 뽑기
        String token = authorizationHeader.substring(7);
        Claims claims = jwtTokenProvider.getClaims(token);
        Long memberId = Long.valueOf(claims.getSubject());

        communityCommentService.deleteCommunityComment(memberId, commentId);
        return ApiResponse.onSuccess(null);
    }

    // 커뮤니티 댓글 전체 조회
    @GetMapping("/{communityId}/comment")
    @Operation(summary = "커뮤니티 댓글/대댓글 전체 조회 API",description = "커뮤니티 하단에 있는 댓글 전체를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "communityId", description = "커뮤니티의 ID, path variable 입니다!")
    })
    public ApiResponse<List<CommunityCommentResponseDTO.GetCommunityCommentListResultDTO>> getCommunityComment(@PathVariable(name = "communityId") Long communityId){
        List<CommunityCommentResponseDTO.GetCommunityCommentListResultDTO> communityCommentList = communityCommentService.getCommunityCommentList(communityId);
        return ApiResponse.onSuccess(communityCommentList);
    }

}
