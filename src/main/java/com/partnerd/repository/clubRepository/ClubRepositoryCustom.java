package com.partnerd.repository.clubRepository;

import com.partnerd.web.dto.homeDTO.response.HomeClubDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClubRepositoryCustom {
    List<HomeClubDTO> findTopClubs(Pageable pageable);
}

