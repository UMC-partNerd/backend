package com.partnerd.web.controller.clubController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.SuccessStatus;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.clubConverter.ClubMemberConverter;
import com.partnerd.mongoRepository.domain.mapping.ClubMember;
import com.partnerd.service.clubMemberService.ClubMemberService;
import com.partnerd.web.dto.clubDTO.*;
import com.partnerd.web.dto.memberDTO.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partnerdMember")
@RequiredArgsConstructor
public class ClubMemberRestController {

    private final ClubMemberService clubMemberService;
    private final JwtTokenProvider jwtTokenProvider;

     // 파트너드(동아리) 리더 권한 위임
    @PutMapping("/{clubId}/{newLeaderId}")
    @Operation(summary = "팀 리더의 권한을 위임 API",description = "팀 리더의 권한을 위임하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "clubId", description = "권한을 위임할 동아리의 ID, path variable 입니다!"),
            @Parameter(name = "newLeaderId", description = "새로운 리더의 memberId, path variable 입니다!")
    })
    public ApiResponse<ClubMemberResponseDTO.ClubChangeLeaderDTO> putChangeClubLeader(@PathVariable(name = "clubId") Long clubId,
                                                                                      @PathVariable(name = "newLeaderId") Long newLeaderId,
                                                                                      @RequestHeader("Authorization") String authorizationHeader){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long leaderId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        ClubMember clubMember = clubMemberService.putChangeClubLeader(clubId, newLeaderId, leaderId);

        return ApiResponse.onSuccess(ClubMemberConverter.changeClubLeaderDTO(clubMember));
    }


    // 파트너드(동아리) 멤버 비활성화
    @PutMapping("{clubId}/{memberId}/inactive")
    @Operation(summary = "팀멤버의 활동 상태를 비활성화로 변경하는 API",description = "팀 리더가 팀원 관리를 통해 동아리 멤버의 활동 상태를 비활성화로 변경하는(탈부) API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "clubId", description = "memberId의 소유자인 동아리 멤버가 활동중인 동아리의 ID, path variable 입니다!"),
            @Parameter(name = "memberId", description = "비활성화 상태로 변경될 동아리 멤버의 memberId, path variable 입니다!")
    })
    public ApiResponse<ClubMemberResponseDTO.ClubChangeActiveDTO> putChangeMemberActvice(@PathVariable(name = "clubId") Long clubId,
                                                                                      @PathVariable(name = "memberId") Long memberId,
                                                                                      @RequestHeader("Authorization") String authorizationHeader){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long leaderId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        ClubMember clubMember = clubMemberService.putChangeMemberActvice(clubId, memberId, leaderId);

        return ApiResponse.onSuccess(ClubMemberConverter.changeMemberActviceDTO(clubMember));
    }

    // 클럽 멤버 조회 API
    @GetMapping("/members/{clubId}")
    @Operation(summary = "클럽 멤버 조회 API", description = "특정 클럽에 속한 멤버 목록을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공적으로 조회되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 클럽이 존재하지 않거나, 멤버가 없습니다.")
    })
    @Parameters({
            @Parameter(name = "clubId", description = "조회할 클럽의 ID", example = "1", required = true)
    })
    public ApiResponse<List<MemberResponseDTO.ClubMemberDTO>> getClubMembers(@PathVariable Long clubId) {
        List<MemberResponseDTO.ClubMemberDTO> members = clubMemberService.getClubMembers(clubId);
        return ApiResponse.of(SuccessStatus._OK, members);
    }
}
