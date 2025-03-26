package com.partnerd.web.controller.clubController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.clubConverter.ClubMembershipRequestConverter;
import com.partnerd.domain.mapping.ClubMembershipRequest;
import com.partnerd.service.clubMembershipRequestService.ClubMembershipRequestService;
import com.partnerd.web.dto.clubDTO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 파트너드(동아리) 가입 요청 거절
    @PutMapping("/reject")
    @Operation(summary = "파트너드(동아리) 가입 요청 거절 API",description = "리더가 파트너드(동아리)에 가입 요청한 사용자의 요청을 거절하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<ClubResponseDTO.ClubJoinResultDTO> putClubJoinReject(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid ClubRequestDTO.ClubJoinDTO request){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long leaderId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        ClubMembershipRequest clubMembershipRequest = clubMembershipRequestService.putClubJoinReject(leaderId, request);

        return ApiResponse.onSuccess(ClubMembershipRequestConverter.clubJoinRejectDTO(clubMembershipRequest));
    }


    // 파트너드(동아리) 가입 요청
    @PostMapping("/{clubId}")
    @Operation(summary = "파트너드(동아리) 가입 요청 API",description = "사용자가 특정 파트너드(동아리)에 가입을 요청하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "clubId", description = "가입하고자 하는 특정 파트너드(동아리)의 ID, path variable 입니다!"),
    })
    public ApiResponse<ClubResponseDTO.ClubJoinRequestResultDTO> addClubMembershipRequest(@RequestHeader("Authorization") String authorizationHeader,
                                                                                          @PathVariable(name = "clubId") Long clubId){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        ClubMembershipRequest clubMembershipRequest = clubMembershipRequestService.addClubMembershipRequest(memberId, clubId);

        return ApiResponse.onSuccess(ClubMembershipRequestConverter.addClubMembershipRequestDTO(clubMembershipRequest));
    }

    // 파트너드(동아리) 가입 요청 목록 조회
    @GetMapping("/{clubId}/requestList")
    @Operation(summary = "파트너드(동아리) 가입 요청 목록 조회 API",description = "파트너드 상세 페이지에서 리더가 파트너드에 요청된 가입 목록을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "clubId", description = "가입 요청 목록을 조회하고자 하는 특정 파트너드(동아리)의 ID, path variable 입니다!"),
    })
    public ApiResponse<ClubResponseDTO.ClubJoinRequestListDTO> getClubJoinRequestList(@RequestHeader("Authorization") String authorizationHeader,
                                                                                      @PathVariable(name = "clubId") Long clubId){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long leaderId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        List<ClubMembershipRequest> clubJoinRequestList = clubMembershipRequestService.getClubJoinRequestList(leaderId, clubId);

        return ApiResponse.onSuccess(ClubMembershipRequestConverter.clubJoinRequestDTOList(clubId, clubJoinRequestList));
    }
}
