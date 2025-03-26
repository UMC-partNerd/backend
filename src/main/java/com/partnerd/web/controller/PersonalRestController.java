package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.PersonalConverter;
import com.partnerd.mongoRepository.domain.Personal;
import com.partnerd.service.personalService.PersonalService;
import com.partnerd.web.dto.personalDTO.PersonalRequestDTO;
import com.partnerd.web.dto.personalDTO.PersonalResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/personal")
public class PersonalRestController {

    private final PersonalService personalService;
    private final JwtTokenProvider jwtTokenProvider;

    // 퍼스널페이지 생성
    @PostMapping("/")
    @Operation(summary = "퍼스널페이지 생성 API", description = "특정 사용자의 퍼스널페이지를 생성 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<PersonalResponseDTO.CreatePersonalResultDTO> addPersonal(@RequestHeader("Authorization") String authorizationHeader,
                                                                            @RequestBody @Valid PersonalRequestDTO.PersonalDTO request) {
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        Personal personal = personalService.addPersonal(request, memberId);

        return ApiResponse.onSuccess(PersonalConverter.toCreatePersonalResultDTO(personal));
    }

    // 퍼스널페이지 조회
    @GetMapping("/")
    @Operation(summary = "퍼스널페이지 조회 API", description = "특정 사용자의 퍼스널페이지(프로젝트 제외)를 조회 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<PersonalResponseDTO.ReadPersonalResultDTO> readPersonal(@RequestHeader("Authorization") String authorizationHeader) {
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        Personal personal = personalService.readPersonal(memberId);

        return ApiResponse.onSuccess(PersonalConverter.toReadPersonalResultDTO(personal));
    }

    // 퍼스널페이지 수정
    @PatchMapping("/")
    @Operation(summary = "퍼스널페이지 수정 API", description = "특정 사용자의 퍼스널페이지를 수정 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<PersonalResponseDTO.UpdatePersonalResultDTO> updatePersonal(@RequestHeader("Authorization") String authorizationHeader,
                                                                                @RequestBody @Valid PersonalRequestDTO.PersonalDTO request) {
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        Personal personal = personalService.updatePersonal(request, memberId);

        return ApiResponse.onSuccess(PersonalConverter.toUpdatePersonalResultDTO(personal));
    }
}