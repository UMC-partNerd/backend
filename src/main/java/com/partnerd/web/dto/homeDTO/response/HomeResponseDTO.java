package com.partnerd.web.dto.homeDTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class HomeResponseDTO {

    private List<HomeCollabPostDTO> recentCollabPosts;
    private List<HomeClubDTO> popularClubs;
    private List<HomeProjectDTO> recentProjects;
    private List<HomePromotionProjectDTO> popularPromotionProjects;
}
