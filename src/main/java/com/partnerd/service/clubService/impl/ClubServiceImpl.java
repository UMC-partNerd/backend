package com.partnerd.service.clubService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CategoryHandler;
import com.partnerd.apiPaylaod.exception.handler.ClubHandler;
import com.partnerd.apiPaylaod.exception.handler.ClubMemberHandler;
import com.partnerd.converter.ClubConverter;
import com.partnerd.domain.Category;
import com.partnerd.domain.Club;
import com.partnerd.domain.ContactMethod;
import com.partnerd.domain.Member;
import com.partnerd.domain.enums.ActiveType;
import com.partnerd.domain.enums.ClubMemberRole;
import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.repository.categoryRepository.CategoryRepository;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepository;
import com.partnerd.repository.clubRepository.ClubRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.service.clubService.ClubService;
import com.partnerd.web.dto.clubDTO.*;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;

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
                                .contactType(contactMethodDTO.getContactType())
                                .contactUrl(contactMethodDTO.getContactUrl())
                                .build();
                        contactMethod.setClub(club);
                        return contactMethod;
                    })
                    .collect(Collectors.toList());

            // Club의 ContactMethodList에 추가
            club.getContactMethodList().addAll(contactMethods);


        }
        // 멤버 ID로 멤버 조회
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ClubHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // ClubMember 객체 생성 및 설정
        ClubMember clubMember = ClubMember.builder()
                .role(ClubMemberRole.LEADER)
                .status(ActiveType.ACTIVE)
                .joined_date(new Date())
                .club(club)
                .member(member)
                .build();

        // Club의 ClubMembers 리스트에 추가
        club.getClubMembers().add(clubMember);



        Club savedClub = clubRepository.save(club);
        return ClubConverter.toClubRegisterResponseDTO(savedClub);
    }

    @Override
    public void deleteClub(Long clubId, Long memberId) {

        //클럽존재여부확인
        if (!clubRepository.existsById(clubId)) {
            throw new ClubHandler(ErrorStatus.CLUB_NOT_FOUND);
        }

        // 클럽 멤버 조회 및 리더 권한 확인
        ClubMember clubMember = clubMemberRepository.findClubMemberByClubIdAndMemberId(clubId, memberId)
                .orElseThrow(() -> new ClubHandler(ErrorStatus.MEMBER_NOT_FOUND));
        if (clubMember.getRole() != ClubMemberRole.LEADER) {
            throw new ClubMemberHandler(ErrorStatus.CLUB_MEMBER_NOT_AUTHORIZED);
        }

        // Delete the club
        clubRepository.deleteById(clubId);
    }

    @Override
    public ClubUpdateResponseDTO updateClub(Long clubId, ClubUpdateRequestDTO dto, Long memberId) {

        //기존 클럽 정보가져오기
        Club existingClub = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubHandler(ErrorStatus.CLUB_NOT_FOUND));

        // 클럽 멤버 조회 및 리더 권한 확인
        ClubMember clubMember = clubMemberRepository.findClubMemberByClubIdAndMemberId(clubId, memberId)
                .orElseThrow(() -> new ClubHandler(ErrorStatus.MEMBER_NOT_FOUND));
        if (clubMember.getRole() != ClubMemberRole.LEADER) {
            throw new ClubMemberHandler(ErrorStatus.CLUB_MEMBER_NOT_AUTHORIZED);
        }

        //카테고리 정보 가져오기
        Category existingCategory = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));

        //Club 기본 정보 업데이트
        existingClub.update(dto.getName(), dto.getIntro(),existingCategory);

        //Contact Method 업데이트

        if(dto.getContactMethod() != null && !dto.getContactMethod().isEmpty()) {
            List<ContactMethod> contactMethodList = dto.getContactMethod().stream()
                    .map( contactMethodDTO -> ContactMethod.builder()
                            .contactType(contactMethodDTO.getContactType())
                            .contactUrl(contactMethodDTO.getContactUrl())
                            .build())
                    .collect(Collectors.toList());

            existingClub.updateContactMethods(contactMethodList);

        }

        // 5. 변경된 클럽 저장
        Club updatedClub = clubRepository.save(existingClub);


        return ClubConverter.toClubUpdateResponseDTO(updatedClub);
    }

    @Override
    public List<ClubDTO> getClubs(Integer page, String sort, Long categoryID){

        Pageable pageable = PageRequest.of(page, 12);
        Page<Club> clubPage;

        if(categoryID != null ){
            //카테고리별 정렬처리
            if("latest".equalsIgnoreCase(sort)){
                clubPage = clubRepository.findByCategoryIdOrderByCreatedAtDesc(categoryID, pageable);

            }
            else{
                clubPage = clubRepository.findByCategoryIdOrderByViewsDesc(categoryID, pageable);
            }

        } else {
            //전체 정렬 처리
            if("latest".equalsIgnoreCase(sort)){
                clubPage = clubRepository.findAllByOrderByCreatedAtDesc(pageable);
            }

            else{
                clubPage = clubRepository.findAllByOrderByViewsDesc(pageable);
            }
        }

        //ClubConverter를 통해 Club을 ClubDTO로 변환시켜서 반환
        return clubPage.stream()
                .map(ClubConverter::toClubDTO)
                .collect(Collectors.toList());




    }

    // 파트너드 목록 조회(마이페이지)
    @Override
    public List<Club> getClubsByRole(Long memberId) {
        return clubMemberRepository.findClubsByRole(memberId, List.of(ClubMemberRole.LEADER, ClubMemberRole.OFFICER));
    }
}