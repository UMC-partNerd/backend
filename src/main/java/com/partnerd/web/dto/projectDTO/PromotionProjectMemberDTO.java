package com.partnerd.web.dto.projectDTO;

import com.partnerd.domain.mapping.PromotionProjectMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionProjectMemberDTO {
    private Long id;
    private String name;

    public PromotionProjectMemberDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PromotionProjectMemberDTO toPromotionProjectMemberDTO(PromotionProjectMember promotionProjectMember) {
        return new PromotionProjectMemberDTO(
                promotionProjectMember.getMember().getId(),
                promotionProjectMember.getMember().getName()
        );
    }

}
