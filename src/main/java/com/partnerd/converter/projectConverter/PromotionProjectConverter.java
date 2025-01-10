package com.partnerd.converter.projectConverter;

import com.partnerd.domain.PromotionProject;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
}
