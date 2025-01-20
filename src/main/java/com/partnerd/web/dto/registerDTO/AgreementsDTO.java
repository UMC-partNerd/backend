package com.partnerd.web.dto.registerDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgreementsDTO {
    @NotNull(message = "성인 여부는 필수입니다.")
    private Boolean isAdult;
    @NotNull(message = "서비스 약관 동의는 필수입니다.")
    private Boolean termsOfServices;
    @NotNull(message = "개인정보 이용 동의는 필수입니다.")
    private Boolean personalInfoUsage;
    private Boolean optionalInfoUsage;
    private Boolean marketingConsent;
    private Boolean marketingNotify;
}
