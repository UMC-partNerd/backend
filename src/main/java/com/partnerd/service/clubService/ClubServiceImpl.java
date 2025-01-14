package com.partnerd.service.clubService;

import com.partnerd.converter.ClubConverter;
import com.partnerd.domain.Category;
import com.partnerd.domain.Club;
import com.partnerd.domain.ContactMethod;
import com.partnerd.repository.categoryRepository.CategoryRepository;
import com.partnerd.repository.clubRepository.ClubRepository;
import com.partnerd.web.dto.clubDTO.ClubRegisterRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubRegisterResponseDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateRequestDTO;
import com.partnerd.web.dto.clubDTO.ClubUpdateResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ClubRegisterResponseDTO registerClub(ClubRegisterRequestDTO dto) {



        //DTO에서받은 카테고리 id로 카테고리 받아옴
        Category category = categoryRepository.findById( (long) dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

        //클럽객체생성
        Club club =ClubConverter.toClubEntity(dto,category);

        //DTO에 있는 컨택트메서드 클럽에 매핑
        if(dto.getContactMethod() != null && !dto.getContactMethod().isEmpty()){



            List<ContactMethod> contactMethods = dto.getContactMethod().stream()
                    .map(contactMethodDTO -> {
                        ContactMethod contactMethod = ContactMethod.builder()
                                .contact_type(contactMethodDTO.getContactType())
                                .contact_url(contactMethodDTO.getContactUrl())
                                .build();
                        contactMethod.setClub(club);
                        return contactMethod;
                    })
                    .collect(Collectors.toList());

            // Club의 ContactMethodList에 추가
            club.getContactMethodList().addAll(contactMethods);


        }

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

        existingClub.update(dto.getName(),dto.getIntro(),dto.getContact(), dto.getCategory());
        Club savedClub = clubRepository.save(existingClub);
        return ClubConverter.toClubUpdateResponseDTO(savedClub);
    }
}