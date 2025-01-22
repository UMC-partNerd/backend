package com.partnerd.converter;

import com.partnerd.domain.Member;
import com.partnerd.domain.Personal;
import com.partnerd.domain.PersonalLink;
import com.partnerd.web.dto.personalLinkDTO.PersonalLinkResponseDTO;
import com.partnerd.web.dto.personalDTO.PersonalRequestDTO;
import com.partnerd.web.dto.personalDTO.PersonalResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonalConverter {

    // 퍼스널페이지 생성
    public static Personal toPersonal(PersonalRequestDTO.CreatePersonalDTO request, Member member){
        List<PersonalLink> personalLinks = new ArrayList<>();
        if (request.getPersonalLinkList() != null) {
            request.getPersonalLinkList().forEach(linkDTO -> {
                PersonalLink link = PersonalLink.builder()
                        .linkUrl(linkDTO.getLinkUrl())
                        .build();
                personalLinks.add(link);
            });
        }

        return Personal.builder()
                .intro(request.getIntro())
                .personalHistory(request.getPersonalHistory())
                .education(request.getEducation())
                .activityProject(request.getActivityProject())
                .skill(request.getSkill())
                .member(member)
                .personalLinkList(personalLinks)
                .build();
    }

    public static PersonalResponseDTO.CreatePersonalResultDTO toCreatePersonalResultDTO(Personal personal) {
        return PersonalResponseDTO.CreatePersonalResultDTO.builder()
                .personalId(personal.getId())
                .intro(personal.getIntro())
                .personalHistory(personal.getPersonalHistory())
                .education(personal.getEducation())
                .activityProject(personal.getActivityProject())
                .skill(personal.getSkill())
                .personalLinkList(personal.getPersonalLinkList().stream()
                        .map(link -> PersonalLinkResponseDTO.CreatePersonalLinkResultDTO.builder()
                                .linkUrl(link.getLinkUrl())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
