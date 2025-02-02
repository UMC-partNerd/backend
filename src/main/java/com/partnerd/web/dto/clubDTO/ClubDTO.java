package com.partnerd.web.dto.clubDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class ClubDTO {
    private Long clubId;
    private String profileImage;  //keyname
    private String name;
    private String intro;


}
