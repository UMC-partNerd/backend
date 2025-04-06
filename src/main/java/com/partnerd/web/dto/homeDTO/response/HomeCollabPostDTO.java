package com.partnerd.web.dto.homeDTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeCollabPostDTO {
    private Long id;
    private String title;
    private String intro;
    private String clubName;
    private String profileImage;

}
