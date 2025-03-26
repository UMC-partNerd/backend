package com.partnerd.converter.projectConverter;

import com.partnerd.mongoRepository.domain.PromotionProject;
import com.partnerd.mongoRepository.domain.PromotionProjectImage;
import com.partnerd.mongoRepository.domain.enums.ImageType;
import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import com.partnerd.web.dto.memberDTO.MemberResponseDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectMemberDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class PromotionProjectConverter {

    // 프로젝트 홍보 생성
    public static PromotionProject toPromotionProject(PromotionProjectRequestDTO.CreatePromotionProjectDTO request){
        return PromotionProject.builder()
                .title(request.getTitle())
                .intro(request.getInfo())
                .views(0L)
                .vote(0L)
                .description(request.getDescription())
                .promotionProjectMemberList(new HashSet<>())
                .build();
    }

    public static PromotionProjectResponseDTO.CreatePromotionProjectResultDTO toCreatePromotionProjectResultDTO(PromotionProject promotionProject){
        return PromotionProjectResponseDTO.CreatePromotionProjectResultDTO.builder()
                .promotionProjectId(promotionProject.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 프로젝트 홍보 수정
    public static PromotionProjectResponseDTO.UpdatePromotionProjectResultDTO toUpdatePromotionProjectResultDTO(PromotionProject promotionProject){
        return PromotionProjectResponseDTO.UpdatePromotionProjectResultDTO.builder()
                .promotionProjectId(promotionProject.getId())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // 프로젝트 홍보글 모아보기 (한칸씩)
    public static PromotionProjectResponseDTO.PromotionProjectPreviewDTO promotionProjectPreviewDTO(PromotionProject promotionProject){

        String thumbnailKeyName = promotionProject.getPromotionProjectImageList().stream()
                .filter(image -> image.getImageType() == ImageType.THUMBNAIL)
                .map(PromotionProjectImage::getKeyName)
                .findFirst()
                .orElse(null);

        return PromotionProjectResponseDTO.PromotionProjectPreviewDTO.builder()
                .promotionProjectId(promotionProject.getId())
                .title(promotionProject.getTitle())
                .intro(promotionProject.getIntro())
                .thumbnailKeyName(thumbnailKeyName)
                .build();
    }

    // 프로젝트 홍보글 모아보기 (전체 리스트)
    public static PromotionProjectResponseDTO.PromotionProjectPreviewListDTO promotionProjectPreviewListDTO(Page<PromotionProject> promotionProjectPage){
        List<PromotionProjectResponseDTO.PromotionProjectPreviewDTO> promotionProjectPreviewDTOList =
                promotionProjectPage.stream().map(PromotionProjectConverter::promotionProjectPreviewDTO).collect(Collectors.toList());

        return PromotionProjectResponseDTO.PromotionProjectPreviewListDTO.builder()
                .promotionProjectPreviewDTOList(promotionProjectPreviewDTOList)
                .listSize(promotionProjectPage.getSize())
                .totalPage(promotionProjectPage.getTotalPages())
                .totalElements(promotionProjectPage.getTotalElements())
                .isFirst(promotionProjectPage.isFirst())
                .isLast(promotionProjectPage.isLast())
                .build();
    }

    // 프로젝트 홍보글 모아보기 (top3)
    public static List<PromotionProjectResponseDTO.PromotionProjectPreviewDTO> projectPreviewDTOList (List<PromotionProject> promotionProjectList){

        return promotionProjectList.stream()
                        .map(PromotionProjectConverter::promotionProjectPreviewDTO)
                        .collect(Collectors.toList());
    }

    // 프로젝트 홍보글 상세페이지 조회
    public static PromotionProjectResponseDTO.PromotionProjectDetailDTO toPromotionProjectDetailDTO(PromotionProject promotionProject, boolean isVoted) {

        String thumbnailKeyName = promotionProject.getPromotionProjectImageList().stream()
                .filter(image -> image.getImageType() == ImageType.THUMBNAIL)
                .map(PromotionProjectImage::getKeyName)
                .findFirst()
                .orElse(null);
        List<String> projectImgKeyNameList = promotionProject.getPromotionProjectImageList().stream()
                .filter(image -> image.getImageType() == ImageType.INTRO)
                .map(PromotionProjectImage::getKeyName)
                .collect(Collectors.toList());

        return PromotionProjectResponseDTO.PromotionProjectDetailDTO.builder()
                .title(promotionProject.getTitle())
                .intro(promotionProject.getIntro())
                .description(promotionProject.getDescription())
                .vote(promotionProject.getVote())
                .isVoted(isVoted)
                .thumbnailKeyName(thumbnailKeyName)
                .projectImgKeyNameList(projectImgKeyNameList)
                .contactMethods(promotionProject.getContactMethodList().stream()
                        .map(ContactMethodDTO::toContactMethodDTO)
                        .collect(Collectors.toSet()))
                .promotionProjectMembers(promotionProject.getPromotionProjectMemberList().stream()
                        .map(PromotionProjectMemberDTO::toPromotionProjectMemberDTO)
                        .collect(Collectors.toSet()))
                .leaderInfo(MemberResponseDTO.MemberForProjectDetailDTO.builder()
                        .id(promotionProject.getMember().getId())
                        .nickname(promotionProject.getMember().getNickname())
                        .profileKeyName(promotionProject.getMember().getProfile_url())
                        .occupation_of_interest(promotionProject.getMember().getOccupation_of_interest())
                        .belong_to_club(promotionProject.getMember().getBelong_to_club())
                        .build())
                .build();
    }


    // 마이페이지 - 내가 쓴 프로젝트 홍보글 모아보기
    public static PromotionProjectResponseDTO.MypagePromotionProjectPreviewListDTO toMyPromotionProjectsDTO(Long memberId, List<PromotionProject> promotionProjects) {
        List<PromotionProjectResponseDTO.MypagePromotionProjectPreviewDTO> promotionProjectPreviewDTOList = promotionProjects.stream()
                .map(promotionProject -> PromotionProjectResponseDTO.MypagePromotionProjectPreviewDTO.builder()
                        .promotionProjectId(promotionProject.getId()) // 프로젝트 ID
                        .title(promotionProject.getTitle()) // 프로젝트 제목
                        .description(promotionProject.getDescription()) // 프로젝트 설명
                        .createdAt(promotionProject.getCreatedAt()) // 생성 날짜
                        .updatedAt(promotionProject.getUpdatedAt()) // 수정 날짜
                        .build())
                .toList();

        return PromotionProjectResponseDTO.MypagePromotionProjectPreviewListDTO.builder()
                .memberId(memberId) // 사용자 ID
                .mypagePromotionPprojectPreviewDTOList(promotionProjectPreviewDTOList) // 변환된 프로젝트 DTO 리스트
                .build();
    }
}
