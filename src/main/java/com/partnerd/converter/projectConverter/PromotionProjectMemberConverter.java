package com.partnerd.converter.projectConverter;

import com.partnerd.domain.Member;
import com.partnerd.domain.mapping.PromotionProjectMember;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PromotionProjectMemberConverter {

    // 프로젝트 홍보 생성
    public static Set<PromotionProjectMember> toPromotionProjectMemberList (Set<Member> memberList){

        return memberList.stream()
               .map(member ->
                       PromotionProjectMember.builder()
                                .member(member)
                                .build()
                ).collect(Collectors.toSet());
    }
}
