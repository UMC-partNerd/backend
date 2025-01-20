package com.partnerd.web.dto.personalDTO;

import com.partnerd.web.dto.PersonalLinkDTO.PersonalLinkRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PersonalRequestDTO {

    // 퍼스널페이지 생성
    @Getter
    @Setter
    public static class CreatePersonalDTO {
        String intro;    // 한 줄 소개
        String personalHistory; // 경력
        String education;    // 학력
        String activityProject;   // 활동 프로젝트
        String skill;    // 스킬
        List<PersonalLinkRequestDTO.PersonalLinkDTO> personalLinkList;  // 링크
    }
}
