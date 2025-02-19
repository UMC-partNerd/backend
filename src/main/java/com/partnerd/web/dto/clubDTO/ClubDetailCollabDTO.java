package com.partnerd.web.dto.clubDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClubDetailCollabDTO {
    private String title;
    private String description;
    private Date openDate; // 추후리팩토링
}
