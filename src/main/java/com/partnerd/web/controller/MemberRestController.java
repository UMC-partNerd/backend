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

    // 내프로필 수정
    @PatchMapping("/users/me/info/{memberId}")
    @Operation(summary = "내프로필 수정 API",description = "마이페이지의 내프로필 정보를 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MYPROFILE200",description = "마이페이지 정보 수정 성공"),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "내프로필 사용자의 ID, path variable 입니다!")
    })
    public ApiResponse<MemberResponseDTO.UpdateMemberResultDTO> updateMember(@PathVariable(name = "memberId") Long memberId, @RequestBody @Valid MemberRequestDTO.UpdateMemberDTO request){

        Member member = memberService.updateMember(request, memberId);
        return ApiResponse.onSuccess(MemberConverter.toUpdateMemberResultDTO(member));
    }
}
