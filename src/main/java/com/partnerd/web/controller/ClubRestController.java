package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.code.status.SuccessStatus;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.ClubConverter;
import com.partnerd.domain.Club;
import com.partnerd.service.clubService.ClubService;
import com.partnerd.web.dto.clubDTO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partnerd")
@RequiredArgsConstructor
public class ClubRestController {

    private final ClubService clubService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    @Operation(summary = "동아리 등록 API", description = "새로운 동아리를 등록하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공적으로 등록되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 데이터가 올바르지 않습니다.")
    })
    public ApiResponse<ClubRegisterResponseDTO> registerClub(
            @RequestHeader("Authorization") String authorizationHeader,
            @ModelAttribute ClubRegisterRequestDTO requestDTO) {

        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        ClubRegisterResponseDTO response = clubService.registerClub(requestDTO);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @DeleteMapping("/{clubId}")
    @Operation(summary = "동아리 삭제 API", description = "동아리를 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공적으로 삭제되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 동아리입니다.")
    })
    @Parameters({
            @Parameter(name = "clubId", description = "파트너드의 ID, path variable 입니다!")
    })
    public ApiResponse<Void> deleteClub(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long clubId) {

        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        clubService.deleteClub(clubId, memberId);
        return ApiResponse.of(SuccessStatus._OK, null);
    }

    @PutMapping("/{clubId}")
    @Operation(summary = "동아리 수정 API", description = "동아리 정보를 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공적으로 수정되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 데이터가 올바르지 않습니다.")
    })
    @Parameters({
            @Parameter(name = "clubId", description = "파트너드의 ID, path variable 입니다!")
    })
    public ApiResponse<ClubUpdateResponseDTO> updateClub(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long clubId,
            @ModelAttribute ClubUpdateRequestDTO requestDTO) {
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());


        ClubUpdateResponseDTO response = clubService.updateClub(clubId, requestDTO,memberId);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @GetMapping
    @Operation(summary = "동아리 전체 조회 API", description = "파트너드 찾기 화면에 들어왔을 때 파트너드 목록을 반환하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공적으로 조회되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 데이터가 올바르지 않습니다.")
    })

    public ApiResponse<List<ClubDTO>> getClubs(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(name = "page")
            @Parameter(description = "조회할 페이지 번호 (1부터 시작)", example = "1", required = true)
            Integer page,

            @RequestParam(defaultValue = "popular")
            @Parameter(description = "정렬 기준 ('popular': 인기순, 'latest': 최신순)", example = "popular", required = false)
            String sort,

            @RequestParam(name = "categoryID")
            @Parameter(description = "카테고리 ID (필터링에 사용)", example = "5", required = true)
            Long categoryID
    ){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        List<ClubDTO> clubs = clubService.getClubs(page -1 ,sort,categoryID);
        return ApiResponse.of(SuccessStatus._OK,clubs);
    }


    // 파트너드 목록 조회(마이페이지)
    @GetMapping("/myPartnerdPosts")
    @Operation(summary = "마이페이지 파트너드 목록 조회 API",description = "마이페이지의 팀관리 페이지에서 파트너드 목록을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<ClubResponseDTO.ReadClubPreviewListDTO> getClubsByRole(@RequestHeader("Authorization") String authorizationHeader){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        List<Club> clubs = clubService.getClubsByRole(memberId);

       return ApiResponse.onSuccess(ClubConverter.clubPreviewListDTO(clubs));
    }
}
