package com.partnerd.web.dto.projectDTO;

import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PromotionProjectRequestDTO {

    // 프로젝트 홍보 생성
    @Getter
    @Setter
    public static class CreatePromotionProjectDTO {
        String title;   // 제목
        String info;    // 한 줄 소개
        String description; // 설명
        List<Long> promotionProjectMember;   // 함께한 팀원
        private List<ContactMethodDTO> contactMethod;   // 컨택트 방법
    }

    // 프로젝트 홍보 수정
    @Getter
    @Setter
    public static class UpdatePromotionProjectDTO {
        String title;   // 제목
        String info;    // 한 줄 소개
        String description; // 설명
        List<Long> promotionProjectMember;   // 함께한 팀원
        private List<ContactMethodDTO> contactMethod;   // 컨택트 방법
    }
}
