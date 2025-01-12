package com.partnerd.service.clubService;

import com.partnerd.converter.ClubConverter;
import com.partnerd.domain.Club;
import com.partnerd.repository.clubRepository.ClubRepository;
import com.partnerd.web.dto.clubDTO.ClubRegisterRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubRegisterResponseDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    @Override
    public ClubRegisterResponseDTO registerClub(ClubRegisterRequestDTO dto) {
        Club club = ClubConverter.toClubEntity(dto);
        Club savedClub = clubRepository.save(club);
        return ClubConverter.toClubRegisterResponseDTO(savedClub);
    }

    @Override
    public void deleteClub(Long clubId) {
        // Check if the club exists
        if (!clubRepository.existsById(clubId)) {
            throw new IllegalArgumentException("해당 동아리가 존재하지 않습니다.");
        }

        // Delete the club
        clubRepository.deleteById(clubId);
    }

    @Override
    public ClubUpdateResponseDTO updateClub(Long clubId, ClubUpdateRequestDTO dto) {
        Club existingClub = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 동아리가 존재하지 않습니다."));

        Club updatedClub = ClubConverter.toClubEntity(dto);
        Club savedClub = clubRepository.save(updatedClub);
        return ClubConverter.toClubUpdateResponseDTO(savedClub);
    }
}