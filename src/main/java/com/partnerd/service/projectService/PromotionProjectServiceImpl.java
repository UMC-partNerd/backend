package com.partnerd.service.projectService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.PromotionProjectHandler;
import com.partnerd.converter.PromotionProjectMemberConverter;
import com.partnerd.converter.projectConverter.PromotionProjectConverter;
import com.partnerd.domain.Member;
import com.partnerd.domain.PromotionProject;
import com.partnerd.domain.mapping.PromotionProjectMember;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.projectRepository.PromotionProjectRepository;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionProjectServiceImpl implements PromotionProjectService {

    private final PromotionProjectRepository promotionProjectRepository;
    private final MemberRepository memberRepository;

    // 프로젝트 홍보 생성
    @Override
    public PromotionProject addPromotionProject(PromotionProjectRequestDTO.CreatePromotionProjectDTO request) {
        PromotionProject newPromotionProject = PromotionProjectConverter.toPromotionProject(request);

        List<Member> memberList = request.getPromotionProjectMember().stream()
                .map(teamMember -> {
                    return memberRepository.findById(teamMember).orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_NOT_FOUND));
                }).collect(Collectors.toList());

        List<PromotionProjectMember> promotionProjectMemberList = PromotionProjectMemberConverter.toPromotionProjectMemberList(memberList);

        promotionProjectMemberList.forEach(promotionProjectMember -> {promotionProjectMember.setPromotionProject(newPromotionProject);});

        return promotionProjectRepository.save(newPromotionProject);
    }
}
