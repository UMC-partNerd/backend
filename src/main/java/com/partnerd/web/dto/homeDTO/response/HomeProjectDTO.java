package com.partnerd.web.dto.homeDTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeProjectDTO {
    private Long id;
    private String profileImage;
    private String title;
    private String intro;
}
