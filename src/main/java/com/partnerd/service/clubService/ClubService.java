package com.partnerd.service.clubService;

import com.partnerd.web.dto.clubDTO.ClubRegisterRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubRegisterResponseDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateResponseDTO;

public interface ClubService {
    ClubRegisterResponseDTO registerClub(ClubRegisterRequestDTO dto);
    void deleteClub(Long clubId);

    ClubUpdateResponseDTO updateClub(Long clubId, ClubUpdateRequestDTO dto);
}