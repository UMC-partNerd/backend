package com.partnerd.web.dto.clubDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubUpdateResponseDTO {
    private Long clubId;
    private String name;
    private Long categoryId;
    private String categoryName;
}
