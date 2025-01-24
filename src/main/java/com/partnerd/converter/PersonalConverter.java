package com.partnerd.converter;

import com.partnerd.domain.Member;
import com.partnerd.domain.Personal;
import com.partnerd.domain.PersonalLink;
import com.partnerd.web.dto.personalLinkDTO.PersonalLinkResponseDTO;
import com.partnerd.web.dto.personalDTO.PersonalRequestDTO;
import com.partnerd.web.dto.personalDTO.PersonalResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonalConverter {

    // 퍼스널페이지 생성
    public static Personal toPersonal(PersonalRequestDTO.PersonalDTO request, Member member){
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
                        .map(link -> PersonalLinkResponseDTO.PersonalLinkResultDTO.builder()
                                .linkUrl(link.getLinkUrl())
                                .build())
                        .collect(Collectors.toList()))
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 퍼스널페이지 조회
    public static PersonalResponseDTO.ReadPersonalResultDTO toReadPersonalResultDTO(Personal personal) {
        return PersonalResponseDTO.ReadPersonalResultDTO.builder()
                .personalId(personal.getId())
                .nickname(personal.getMember().getNickname())
                .profile_url(personal.getMember().getProfile_url())
                .occupation_of_interest(personal.getMember().getOccupation_of_interest())
                .belong_to_club(personal.getMember().getBelong_to_club())
                .intro(personal.getIntro())
                .personalHistory(personal.getPersonalHistory())
                .education(personal.getEducation())
                .activityProject(personal.getActivityProject())
                .skill(personal.getSkill())
                .personalLinkList(personal.getPersonalLinkList().stream()
                        .map(link -> PersonalLinkResponseDTO.PersonalLinkResultDTO.builder()
                                .linkUrl(link.getLinkUrl())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    // 퍼스널페이지 수정
    public static PersonalResponseDTO.UpdatePersonalResultDTO toUpdatePersonalResultDTO(Personal personal) {
        return PersonalResponseDTO.UpdatePersonalResultDTO.builder()
                .personalId(personal.getId())
                .intro(personal.getIntro())
                .personalHistory(personal.getPersonalHistory())
                .education(personal.getEducation())
                .activityProject(personal.getActivityProject())
                .skill(personal.getSkill())
                .personalLinkList(personal.getPersonalLinkList().stream()
                        .map(link -> PersonalLinkResponseDTO.PersonalLinkResultDTO.builder()
                                .linkUrl(link.getLinkUrl())
                                .build())
                        .collect(Collectors.toList()))
                .updateAt(LocalDateTime.now())
                .build();
    }
}
