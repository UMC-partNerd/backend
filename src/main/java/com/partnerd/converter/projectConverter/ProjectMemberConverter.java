package com.partnerd.converter.projectConverter;

import com.partnerd.domain.Member;
import com.partnerd.domain.mapping.ProjectMember;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectMemberConverter {

    // 프로젝트 모집글 생성
    public static Set<ProjectMember> toProjectMemberList (Set<Member> memberList){

        return memberList.stream()
               .map(member ->
                       ProjectMember.builder()
                                .member(member)
                                .build()
                ).collect(Collectors.toSet());
    }
}
