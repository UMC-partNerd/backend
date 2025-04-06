package com.partnerd.web.dto.homeDTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeClubDTO {
    private Long id;
    private String profileImage;
    private String name;
    private String intro;
    private String categoryName;

}
