package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.MemberConverter;
import com.partnerd.mongoRepository.domain.Member;
import com.partnerd.service.memberService.MemberService;
import com.partnerd.web.dto.memberDTO.MemberResponseDTO;
import com.partnerd.web.dto.memberDTO.MemberRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/users")
public class MemberRestController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    // 내프로필 조회
    @GetMapping("/me/info")
    @Operation(summary = "내프로필 조회 API",description = "마이페이지의 내프로필 정보를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<MemberResponseDTO.ReadMemberResultDTO> readMember(@RequestHeader("Authorization") String authorizationHeader){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        Member member = memberService.readMember(memberId);
        return ApiResponse.onSuccess(MemberConverter.toReadMemberResultDTO(member));
    }

    // 내프로필 수정
    @PatchMapping("/me/info")
    @Operation(summary = "내프로필 수정 API",description = "마이페이지의 내프로필 정보를 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<MemberResponseDTO.UpdateMemberResultDTO> updateMember(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid MemberRequestDTO.UpdateMemberDTO request){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        Member member = memberService.updateMember(request, memberId);
        return ApiResponse.onSuccess(MemberConverter.toUpdateMemberResultDTO(member));
    }

    @GetMapping("/nickname/check")
    @Operation(summary = "닉네임 중복 확인 API", description = "닉네임 중복 여부를 확인하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다.")
    })
    @Parameters({
            @Parameter(name = "nickname", description = "중복 확인할 닉네임, query parameter 입니다!")
    })
    public ApiResponse<Boolean> checkNickname(@RequestParam String nickname) {
        boolean isDuplicate = memberService.isNicknameDuplicate(nickname);
        return ApiResponse.onSuccess(isDuplicate);
    }

}
