package com.partnerd.web.dto.clubDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubDetailMemberDTO {
    private String name;
    private String role; // "LEADER", "MEMBER"
    private String interest; // 멤버 관심 분야 (@PM, @Developer 등)
    private String club; // 클럽 이름
}
