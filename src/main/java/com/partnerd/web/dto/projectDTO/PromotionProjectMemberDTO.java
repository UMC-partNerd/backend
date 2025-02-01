package com.partnerd.web.dto.projectDTO;

import com.partnerd.domain.mapping.PromotionProjectMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionProjectMemberDTO {
    private Long id;
    private String nickname;
    private String profileImg;

    public PromotionProjectMemberDTO(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.profileImg = null;
    }

    public static PromotionProjectMemberDTO toPromotionProjectMemberDTO(PromotionProjectMember promotionProjectMember) {
        return new PromotionProjectMemberDTO(
                promotionProjectMember.getMember().getId(),
                promotionProjectMember.getMember().getNickname()
        );
    }

}
