package com.partnerd.converter.projectConverter;

import com.partnerd.domain.PromotionProject;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PromotionProjectConverter {

    // 프로젝트 홍보 생성
    public static PromotionProject toPromotionProject(PromotionProjectRequestDTO.CreatePromotionProjectDTO request){
        return PromotionProject.builder()
                .title(request.getTitle())
                .intro(request.getInfo())
                .description(request.getDescription())
                .promotionProjectMemberList(new ArrayList<>())
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
         return PromotionProjectResponseDTO.PromotionProjectPreviewDTO.builder()
                .promotionProjectId(promotionProject.getId())
                .title(promotionProject.getTitle())
                .intro(promotionProject.getIntro())
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
}
