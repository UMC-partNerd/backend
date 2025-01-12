package com.partnerd.web.controller;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.code.status.SuccessStatus;
import com.partnerd.service.clubService.ClubService;
import com.partnerd.web.dto.clubDTO.ClubRegisterRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubRegisterResponseDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partnerd")
@RequiredArgsConstructor
public class ClubRestController {

    private final ClubService clubService;

    @PostMapping("/register")
    public ApiResponse<ClubRegisterResponseDTO> registerClub(
            @ModelAttribute ClubRegisterRequestDTO requestDTO) {
        ClubRegisterResponseDTO response = clubService.registerClub(requestDTO);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @DeleteMapping("/{clubId}")
    public ApiResponse<Void> deleteClub(@PathVariable Long clubId) {
        try {
            clubService.deleteClub(clubId);
            return ApiResponse.of(SuccessStatus._OK, null);
        } catch (IllegalArgumentException ex) {
            return ApiResponse.onFailure(
                    ErrorStatus._BAD_REQUEST.getCode(),
                    ex.getMessage(),
                    null
            );
        }
    }

    @PutMapping("/{clubId}")
    public ApiResponse<ClubUpdateResponseDTO> updateClub(
            @PathVariable Long clubId,
            @ModelAttribute ClubUpdateRequestDTO requestDTO) {
        try {
            ClubUpdateResponseDTO response = clubService.updateClub(clubId, requestDTO);
            return ApiResponse.of(SuccessStatus._OK, response);
        } catch (IllegalArgumentException ex) {
            return ApiResponse.onFailure(
                    ErrorStatus._BAD_REQUEST.getCode(),
                    ex.getMessage(),
                    null
            );
        }
    }
}
