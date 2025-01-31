package com.partnerd.web.dto.projectDTO;

import com.partnerd.domain.mapping.PromotionProjectMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionProjectMemberDTO {
    private Long id;
    private String name;
    private String profileImg;

    public PromotionProjectMemberDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.profileImg = null;
    }

    public static PromotionProjectMemberDTO toPromotionProjectMemberDTO(PromotionProjectMember promotionProjectMember) {
        return new PromotionProjectMemberDTO(
                promotionProjectMember.getMember().getId(),
                promotionProjectMember.getMember().getName()
        );
    }

}
