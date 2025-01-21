package com.partnerd.web.dto.registerDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;





@Data
public class RegisterRequestDTO {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "생년월일은 필수 입력 항목입니다.")
    @Pattern(regexp = "\\d{8}", message = "생년월일은 YYYYMMDD 형식이어야 합니다.")
    private String birthDate;

    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하이어야 합니다.")
    private String nickname;

    @NotNull(message = "약관 정보는 필수입니다.")
    private AgreementsDTO agreements;
}
