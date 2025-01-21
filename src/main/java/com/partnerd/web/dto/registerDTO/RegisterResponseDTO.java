package com.partnerd.web.dto.registerDTO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RegisterResponseDTO {
    private Long userId;
    private String name;
    private String nickname;
    private Date birthDate;
}
