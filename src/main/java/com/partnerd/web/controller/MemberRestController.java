package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.converter.MemberConverter;
import com.partnerd.domain.Member;
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
@RequestMapping("/api")
public class MemberRestController {

    private final MemberService memberService;

    // 내프로필 조회
    @GetMapping("/users/me/info/{memberId}")
    @Operation(summary = "내프로필 조회 API",description = "마이페이지의 내프로필 정보를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "내프로필 사용자의 ID, path variable 입니다!")
    })
    public ApiResponse<MemberResponseDTO.ReadMemberResultDTO> readMember(@PathVariable(name = "memberId") Long memberId){

        Member member = memberService.readMember(memberId);
        return ApiResponse.onSuccess(MemberConverter.toReadMemberResultDTO(member));
    }

    // 내프로필 수정
    @PatchMapping("/users/me/info/{memberId}")
    @Operation(summary = "내프로필 수정 API",description = "마이페이지의 내프로필 정보를 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "내프로필 사용자의 ID, path variable 입니다!")
    })
    public ApiResponse<MemberResponseDTO.UpdateMemberResultDTO> updateMember(@PathVariable(name = "memberId") Long memberId, @RequestBody @Valid MemberRequestDTO.UpdateMemberDTO request){

        Member member = memberService.updateMember(request, memberId);
        return ApiResponse.onSuccess(MemberConverter.toUpdateMemberResultDTO(member));
    }

    @GetMapping("/users/nickname/check")
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
