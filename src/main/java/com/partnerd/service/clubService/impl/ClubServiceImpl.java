package com.partnerd.service.clubService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CategoryHandler;
import com.partnerd.apiPaylaod.exception.handler.ClubHandler;
import com.partnerd.converter.ClubConverter;
import com.partnerd.domain.Category;
import com.partnerd.domain.Club;
import com.partnerd.domain.ContactMethod;
import com.partnerd.repository.categoryRepository.CategoryRepository;
import com.partnerd.repository.clubRepository.ClubRepository;
import com.partnerd.service.clubService.ClubService;
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
                .orElseThrow(() -> new ClubHandler(ErrorStatus.CATEGORY_NOT_FOUND));

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

        //클럽존재여부확인
        if (!clubRepository.existsById(clubId)) {
            throw new ClubHandler(ErrorStatus.CLUB_NOT_FOUND);
        }

        // Delete the club
        clubRepository.deleteById(clubId);
    }

    @Override
    public ClubUpdateResponseDTO updateClub(Long clubId, ClubUpdateRequestDTO dto) {

        //기존 클럽 정보가져오기
        Club existingClub = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubHandler(ErrorStatus.CLUB_NOT_FOUND));

        //카테고리 정보 가져오기
        Category existingCategory = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));

        //Club 기본 정보 업데이트
        existingClub.update(dto.getName(), dto.getIntro(),existingCategory);

        //Contact Method 업데이트

        if(dto.getContactMethod() != null && !dto.getContactMethod().isEmpty()) {
            List<ContactMethod> contactMethodList = dto.getContactMethod().stream()
                    .map( contactMethodDTO -> ContactMethod.builder()
                            .contact_type(contactMethodDTO.getContactType())
                            .contact_url(contactMethodDTO.getContactUrl())
                            .build())
                    .collect(Collectors.toList());

            existingClub.updateContactMethods(contactMethodList);

        }

        // 5. 변경된 클럽 저장
        Club updatedClub = clubRepository.save(existingClub);


        return ClubConverter.toClubUpdateResponseDTO(updatedClub);
    }

}