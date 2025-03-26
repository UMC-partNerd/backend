package com.partnerd.web.controller.communityPostController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CommunityHandler;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.communityConverter.CommunityConverter;
import com.partnerd.mongoRepository.domain.Community;
import com.partnerd.service.communityService.CommunityCommandService;
import com.partnerd.service.communityService.CommunityQueryService;
import com.partnerd.web.dto.CommunityDTO.CommunityRequestDTO;
import com.partnerd.web.dto.CommunityDTO.CommunityResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityRestController {

    private final CommunityCommandService communityCommandService;
    private final CommunityQueryService communityQueryService;
    private final JwtTokenProvider jwtTokenProvider;

    // 커뮤니티 글 생성
    @PostMapping
    @Operation(summary = "커뮤니티 글 생성 API", description = "커뮤니티 글을 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<CommunityResponseDTO.addResponseCommunityDTO> addCollabPost(@RequestHeader("Authorization") String authorizationHeader,
                                                                                   @RequestBody CommunityRequestDTO.addRequestCommunityDTO requestDTO) {

        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new CommunityHandler(ErrorStatus.TOKEN_EXPIRED);

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        Community community = communityCommandService.addCommunity(memberId, requestDTO);

        return ApiResponse.onSuccess(CommunityConverter.toCommunityResultDTO(community));
    }


    // 커뮤니티 글 수정
    @PatchMapping("/{communityId}")
    @Operation(summary = "커뮤니티 글 수정 API", description = "커뮤니티 글을 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<CommunityResponseDTO.addResponseCommunityDTO> modifyCommunity(@RequestHeader("Authorization") String authorizationHeader,
                                                                                     @PathVariable(name = "communityId") Long communityId,
                                                                                     @RequestBody CommunityRequestDTO.addRequestCommunityDTO requestDTO) {
        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new CommunityHandler(ErrorStatus.TOKEN_EXPIRED);

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        Community community = communityCommandService.modifyCommunity(memberId, communityId, requestDTO);

        return ApiResponse.onSuccess(CommunityConverter.toCommunityResultDTO(community));
    }

    // 커뮤니티 글 삭제
    @DeleteMapping("/{communityId}")
    @Operation(summary = "커뮤니티 글 삭제 API", description = "커뮤니티 글을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<Long> deleteCommunity(@RequestHeader("Authorization") String authorizationHeader,
                                                                                     @PathVariable(name = "communityId") Long communityId,
                                                                                     @RequestBody CommunityRequestDTO.addRequestCommunityDTO requestDTO) {
        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new CommunityHandler(ErrorStatus.TOKEN_EXPIRED);

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        communityCommandService.deleteCommunity(memberId, communityId);

        return ApiResponse.onSuccess(communityId);
    }



    // 커뮤니티 좋아요
    @PatchMapping("/{communityId}/likes")
    @Operation(summary = "커뮤니티 좋아요/좋아요 취소 API",description = "커뮤니티의 좋아요 개수 증가/감소하는 API입니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CommunityResponseDTO.responseLikesDTO> communityLikes (@RequestHeader("Authorization") String authorizationHeader,
                                             @PathVariable(name = "communityId") Long communityId) {

        // 토큰 에러 처리
        if (authorizationHeader == null || authorizationHeader.isEmpty())
            throw new CommunityHandler(ErrorStatus.TOKEN_EXPIRED);

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        Community community = communityCommandService.communityLikes(memberId, communityId);

        return ApiResponse.onSuccess(CommunityConverter.toLikesResultDTO(community));
    }

    // 커뮤니티 글 전체 조회
    @GetMapping
    @Operation(summary = "커뮤니티 글 전체 조회 API",description = "커뮤니티 글 전체 조회 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CommunityResponseDTO.CommunityPreviewListDTO> getAllCommunity (@RequestParam(required = false) Long cursor, // 이전 페이지의 마지막 ID (없으면 첫 페이지)
                                                        @RequestParam(defaultValue = "30") int size) { // 한 페이지에 가져올 데이터 개수

        return ApiResponse.onSuccess(communityQueryService.getCommunityList(cursor, size));
    }

    // 커뮤니티 인기글 TOP 10 조회
    @GetMapping("/popularity")
    @Operation(summary = "커뮤니티 인기글 TOP 10 조회 API",description = "커뮤니티 인기글 TOP 10 조회 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<List<CommunityResponseDTO.CommunityTop10PreviewDTO>> getTop10Community () {
        return ApiResponse.onSuccess(communityQueryService.getCommunityTop10List());
    }
    
    // 마이페이지 - 내가 쓴 커뮤니티 모집글 모아보기
    @GetMapping("/mypage")
    @Operation(summary = "마이페이지 내가 쓴 커뮤니티 목록 조회 API",description = "마이페이지의 내가 쓴 글 페이지에서 커뮤니티 목록을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CommunityResponseDTO.MypageCommunityPreviewListDTO> getMyCommunities(@RequestHeader("Authorization") String authorizationHeader){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        List<Community> communities = communityQueryService.getMyCommunities(memberId);

        return ApiResponse.onSuccess(CommunityConverter.toMyCommunitiesDTO(memberId, communities));
    }
}
