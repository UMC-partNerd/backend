package com.partnerd.converter;

import com.partnerd.domain.Member;
import com.partnerd.domain.Personal;
import com.partnerd.domain.PersonalLink;
import com.partnerd.domain.Project;
import com.partnerd.web.dto.PersonalLinkDTO.PersonalLinkResponseDTO;
import com.partnerd.web.dto.categoryDTO.CategoryDTO;
import com.partnerd.web.dto.personalDTO.PersonalRequestDTO;
import com.partnerd.web.dto.personalDTO.PersonalResponseDTO;
import com.partnerd.web.dto.projectDTO.ProjectResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonalConverter {

    // 퍼스널페이지 생성
    public static Personal toPersonal(PersonalRequestDTO.CreatePersonalDTO request, Member member){
        // Personal 엔티티 생성
        Personal personal = Personal.builder()
                .intro(request.getIntro())
                .personalHistory(request.getPersonalHistory())
                .education(request.getEducation())
                .activityProject(request.getActivityProject())
                .skill(request.getSkill())
                .member(member) // Member 설정
                .personalLinkList(new ArrayList<>()) // 초기 링크 리스트 생성
                .build();


        if (request.getPersonalLinkList() != null) {
            request.getPersonalLinkList().forEach(linkDTO -> {
                PersonalLink link = PersonalLink.builder()
                        .linkUrl(linkDTO.getLinkUrl())
                        .build();
                personal.addLink(link);
            });
        }

        return personal;
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
