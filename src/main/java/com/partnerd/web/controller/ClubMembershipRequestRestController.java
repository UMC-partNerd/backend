package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.ClubMembershipRequestConverter;
import com.partnerd.domain.mapping.ClubMembershipRequest;
import com.partnerd.service.clubMembershipRequestService.ClubMembershipRequestService;
import com.partnerd.web.dto.clubDTO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partnerdRequest")
@RequiredArgsConstructor
public class ClubMembershipRequestRestController {

    private final ClubMembershipRequestService clubMembershipRequestService;
    private final JwtTokenProvider jwtTokenProvider;

    // 파트너드(동아리) 가입 요청 승인
    @PutMapping("/approve")
    @Operation(summary = "파트너드(동아리) 가입 요청 승인 API",description = "리더가 파트너드(동아리)에 가입 요청한 사용자의 요청을 승인하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<ClubResponseDTO.ClubJoinResultDTO> putClubJoinApprove(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid ClubRequestDTO.ClubJoinDTO request){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long leaderId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        ClubMembershipRequest clubMembershipRequest = clubMembershipRequestService.putClubJoinApprove(leaderId, request);

        return ApiResponse.onSuccess(ClubMembershipRequestConverter.clubJoinApproveDTO(clubMembershipRequest));
    }

}
