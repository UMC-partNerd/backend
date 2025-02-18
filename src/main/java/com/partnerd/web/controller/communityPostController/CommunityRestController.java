package com.partnerd.web.controller.communityPostController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CommunityHandler;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.communityConverter.CommunityConverter;
import com.partnerd.domain.Community;
import com.partnerd.service.communityService.CommunityCommandService;
import com.partnerd.web.dto.CommunityDTO.CommunityRequestDTO;
import com.partnerd.web.dto.CommunityDTO.CommunityResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/community/post")
@RequiredArgsConstructor
public class CommunityRestController {

    private final CommunityCommandService communityCommandService;
    private final JwtTokenProvider jwtTokenProvider;

    // 커뮤니티 글 생성
    @PostMapping
    @Operation(summary = "커뮤니티 글 생성 API",description = "커뮤니티 글을 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
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




}
