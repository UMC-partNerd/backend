package com.partnerd.web.dto.projectDTO;

import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import lombok.*;

import java.util.List;
import java.util.Set;

public class PromotionProjectRequestDTO {

    // 프로젝트 홍보 생성
    @Getter
    @Setter
    public static class CreatePromotionProjectDTO {
        private String title;   // 제목
        private String info;    // 한 줄 소개
        private String description; // 설명
        private List<Long> promotionProjectMember;   // 함께한 팀원
        private Set<ContactMethodDTO> contactMethod;   // 컨택트 방법
    }

    // 프로젝트 홍보 수정
    @Getter
    @Setter
    public static class UpdatePromotionProjectDTO {
        private String title;   // 제목
        private String info;    // 한 줄 소개
        private String description; // 설명
        private List<Long> promotionProjectMember;   // 함께한 팀원
        private Set<ContactMethodDTO> contactMethod;   // 컨택트 방법
    }
}
