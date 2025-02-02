package com.partnerd.web.dto.clubDTO;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
public class ClubDetailCollabDTO {
    private String title;
    private String description;
    private Date openDate; // 추후리팩토링
}
