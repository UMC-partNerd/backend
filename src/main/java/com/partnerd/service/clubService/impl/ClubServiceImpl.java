package com.partnerd.service.clubService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CategoryHandler;
import com.partnerd.apiPaylaod.exception.handler.ClubHandler;
import com.partnerd.apiPaylaod.exception.handler.ClubMemberHandler;
import com.partnerd.converter.clubConverter.ClubConverter;
import com.partnerd.domain.*;
import com.partnerd.domain.enums.ClubMemberRole;
import com.partnerd.domain.enums.ImageType;
import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.repository.categoryRepository.CategoryRepository;
import com.partnerd.repository.clubMemberRepository.ClubMemberRepository;
import com.partnerd.repository.clubRepository.ClubActivityImageRepository;
import com.partnerd.repository.clubRepository.ClubActivityRepository;
import com.partnerd.repository.clubRepository.ClubRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.service.clubService.ClubService;
import com.partnerd.web.dto.clubDTO.*;
import com.partnerd.web.dto.memberDTO.MemberNickNameSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubActivityRepository clubActivityRepository;
    private final ClubActivityImageRepository clubActivityImageRepository;

    @Override
    public ClubRegisterResponseDTO registerClub(ClubRegisterRequestDTO dto , Long memberId) {


        // 1. DTO에서받은 카테고리 id로 카테고리 받아옴
        Category category = categoryRepository.findById( (long) dto.getCategoryId())
                .orElseThrow(() -> new ClubHandler(ErrorStatus.CATEGORY_NOT_FOUND));

        // 2. 클럽객체생성
        Club club =ClubConverter.toClubEntity(dto,category);



        // 3. 연락 방법 추가
        if (dto.getContactMethod() != null) {
            dto.getContactMethod().forEach(contactMethodDTO ->
                    club.addContactMethod(ContactMethod.builder()
                            .contactType(contactMethodDTO.getContactType())
                            .contactUrl(contactMethodDTO.getContactUrl())
                            .build())
            );
        }

        // 4. 클럽 이미지 (배너, 프로필) 저장
        if (dto.getBannerKeyName() != null) club.addClubImage(dto.getBannerKeyName(), ImageType.BANNER);
        if (dto.getMainKeyName() != null) club.addClubImage(dto.getMainKeyName(), ImageType.MAIN);


        // 5. 멤버 조회 및 리더 추가
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ClubHandler(ErrorStatus.MEMBER_NOT_FOUND));
        club.addMember(member, ClubMemberRole.LEADER);

        // 6. 동아리 활동 추가
        if (dto.getActivity() != null) {
            ClubActivity clubActivity = ClubConverter.toClubActivityEntity(dto.getActivity());
            club.addActivity(clubActivity);

            if (dto.getActivity().getActivityImageKeyNames() != null) {
                clubActivity.addActivityImages(dto.getActivity().getActivityImageKeyNames());
            }
        }

        Club savedClub=clubRepository.save(club);
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

        // 1. 기존 클럽 정보 조회
        Club existingClub = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubHandler(ErrorStatus.CLUB_NOT_FOUND));

        // 2. 리더 권한 확인
        ClubMember clubMember = clubMemberRepository.findClubMemberByClubIdAndMemberId(clubId, memberId)
                .orElseThrow(() -> new ClubHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (clubMember.getRole() != ClubMemberRole.LEADER) {
            throw new ClubMemberHandler(ErrorStatus.CLUB_MEMBER_NOT_AUTHORIZED);
        }

        // 3. 카테고리 업데이트
        Category existingCategory = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryHandler(ErrorStatus.CATEGORY_NOT_FOUND));

        existingClub.update(dto.getName(), dto.getIntro(), existingCategory);

        // 4. 연락 방법 업데이트 (기존 삭제 후 추가)
        if (dto.getContactMethod() != null) {
            existingClub.getContactMethodList().clear();
            dto.getContactMethod().forEach(contactMethodDTO ->
                    existingClub.addContactMethod(
                            ContactMethod.builder()
                                    .contactType(contactMethodDTO.getContactType())
                                    .contactUrl(contactMethodDTO.getContactUrl())
                                    .build())
            );
        }

        // 5. 배너 및 프로필 이미지 업데이트 (기존 삭제 후 새 리스트 추가)
        existingClub.getClubImgList().clear(); // 🔥 기존 이미지 모두 삭제

        if (dto.getBannerKeyName() != null) {
            existingClub.getClubImgList().add(
                    ClubImage.builder()
                            .keyName(dto.getBannerKeyName())
                            .image_type(ImageType.BANNER)
                            .club(existingClub)
                            .build()
            );
        }

        if (dto.getMainKeyName() != null) {
            existingClub.getClubImgList().add(
                    ClubImage.builder()
                            .keyName(dto.getMainKeyName())
                            .image_type(ImageType.MAIN)
                            .club(existingClub)
                            .build()
            );
        }

        // 6. 활동(ClubActivity) 업데이트
        if (dto.getActivity() != null) {
            ClubActivity existingActivity = existingClub.getActivity();

            if (existingActivity == null) {
                // 기존 활동이 없으면 새로 생성
                existingActivity = ClubConverter.toClubActivityEntity(dto.getActivity());
                existingClub.addActivity(existingActivity);
            } else {
                // 기존 활동 intro 업데이트
                if (!existingActivity.getIntro().equals(dto.getActivity().getIntro())) {
                    existingActivity.updateActivity(dto.getActivity().getIntro());
                }
                // 기존 활동 이미지 삭제 후 다시 추가
                existingActivity.clearActivityImages();
            }

            // 7. 활동 이미지(ClubActivityImage) 업데이트 (새 리스트 추가)
            if (dto.getActivity().getActivityImageKeyNames() != null) {
                existingActivity.addActivityImages(dto.getActivity().getActivityImageKeyNames());
            }
        }

        // 변경 사항 저장
        clubRepository.save(existingClub);

        return ClubConverter.toClubUpdateResponseDTO(existingClub);
    }

    //파트너드 목록 조회
    @Override
    public List<ClubDTO> getClubs(Integer page, String sort, Long categoryID){

        return clubRepository.findClubsByFilters(page, sort, categoryID);

    }

    // 파트너드 상세조회 (팀페이지)
    @Override
    public ClubDetailResponseDTO findClubDetails(Long clubId, Long memberId) {
        return clubRepository.findClubDetails(clubId, memberId);
    }

    //멤버 전체조회 (파트너드 등록시 필요)
    @Override
    public List<MemberNickNameSearchDTO> searchMembersByNickname(String nickname) {
        List<Member> members = memberRepository.findByNicknameContaining(nickname);

        if (members.isEmpty()) {
            throw new ClubHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        return members.stream()
                .map(member -> new MemberNickNameSearchDTO(member.getNickname(), member.getProfile_url()))
                .collect(Collectors.toList());
    }

    // 파트너드 목록 조회(마이페이지)
    @Override
    public List<Club> getClubsByRole(Long memberId) {
        return clubMemberRepository.findClubsByRole(memberId, List.of(ClubMemberRole.LEADER, ClubMemberRole.OFFICER));
    }

}