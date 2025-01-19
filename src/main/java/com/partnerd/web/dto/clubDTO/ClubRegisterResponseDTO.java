package com.partnerd.web.dto.clubDTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClubRegisterResponseDTO {
    private Long clubId;
    private String name;
    private Long categoryId;
    private String categoryName;
}

