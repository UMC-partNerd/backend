package com.partnerd.web.dto.clubDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubDTO {
    private Long clubId;
    private String profileImage;  //keyname
    private String name;
    private String intro;
    private Long categoryId;


}
