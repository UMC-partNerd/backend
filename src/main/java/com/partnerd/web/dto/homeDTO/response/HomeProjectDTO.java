package com.partnerd.web.dto.homeDTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HomeProjectDTO {
    private Long id;
    private String profileImage;
    private String title;
    private String intro;
}
