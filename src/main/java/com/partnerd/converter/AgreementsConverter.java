package com.partnerd.converter;

import com.partnerd.mongoRepository.domain.Agreements;
import com.partnerd.web.dto.registerDTO.AgreementsDTO;

public class AgreementsConverter {

    public static Agreements toAgreementsEntity(AgreementsDTO dto) {
        return Agreements.builder()
                .is_adult(dto.getIsAdult() != null ? dto.getIsAdult() : false)
                .terms_of_services(dto.getTermsOfServices() != null ? dto.getTermsOfServices() : false)
                .personal_info_usage(dto.getPersonalInfoUsage() != null ? dto.getPersonalInfoUsage() : false)
                .optional_info_usage(dto.getOptionalInfoUsage() != null ? dto.getOptionalInfoUsage() : false)
                .marketing_consent(dto.getMarketingConsent() != null ? dto.getMarketingConsent() : false)
                .marketing_notify(dto.getMarketingNotify() != null ? dto.getMarketingNotify() : false)
                .build();
    }
}
