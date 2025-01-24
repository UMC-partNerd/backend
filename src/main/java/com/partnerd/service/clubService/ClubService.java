package com.partnerd.service.clubService;

import com.partnerd.web.dto.clubDTO.*;

import java.util.List;

public interface ClubService {
    ClubRegisterResponseDTO registerClub(ClubRegisterRequestDTO dto);
    void deleteClub(Long clubId, Long memberId);

    ClubUpdateResponseDTO updateClub(Long clubId, ClubUpdateRequestDTO dto, Long memberId);

    List<ClubDTO> getClubs(Integer page, String sort, Long categoryId);
}