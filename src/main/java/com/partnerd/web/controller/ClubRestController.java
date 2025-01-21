package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.code.status.SuccessStatus;
import com.partnerd.service.clubService.ClubService;
import com.partnerd.web.dto.clubDTO.ClubRegisterRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubRegisterResponseDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partnerd")
@RequiredArgsConstructor
public class ClubRestController {

    private final ClubService clubService;

    @PostMapping("/register")
    @Operation(summary = "동아리 등록 API", description = "새로운 동아리를 등록하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공적으로 등록되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 데이터가 올바르지 않습니다.")
    })
    public ApiResponse<ClubRegisterResponseDTO> registerClub(
            @ModelAttribute ClubRegisterRequestDTO requestDTO) {
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
    public ApiResponse<Void> deleteClub(@PathVariable Long clubId) {
        clubService.deleteClub(clubId);
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
            @PathVariable Long clubId,
            @ModelAttribute ClubUpdateRequestDTO requestDTO) {
        ClubUpdateResponseDTO response = clubService.updateClub(clubId, requestDTO);
        return ApiResponse.of(SuccessStatus._OK, response);
    }
}
