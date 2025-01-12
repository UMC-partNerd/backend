package com.partnerd.service.projectService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.PromotionProjectHandler;
import com.partnerd.converter.projectConverter.PromotionProjectMemberConverter;
import com.partnerd.converter.projectConverter.PromotionProjectConverter;
import com.partnerd.domain.Member;
import com.partnerd.domain.PromotionProject;
import com.partnerd.domain.mapping.PromotionProjectMember;
import com.partnerd.repository.projectRepository.PromotionProjectMemberRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.projectRepository.PromotionProjectRepository;
import com.partnerd.web.dto.projectDTO.PromotionProjectRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionProjectServiceImpl implements PromotionProjectService {

    private final PromotionProjectRepository promotionProjectRepository;
    private final PromotionProjectMemberRepository promotionProjectMemberRepository;
    private final MemberRepository memberRepository;

    // 프로젝트 홍보 생성
    @Override
    public PromotionProject addPromotionProject(PromotionProjectRequestDTO.CreatePromotionProjectDTO request) {
        PromotionProject newPromotionProject = PromotionProjectConverter.toPromotionProject(request);

        List<Member> memberList = request.getPromotionProjectMember().stream()
                .map(memberId -> memberRepository.findById(memberId)
                        .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_ID_NOT_FOUND)))
                .collect(Collectors.toList());

        List<PromotionProjectMember> promotionProjectMemberList = PromotionProjectMemberConverter.toPromotionProjectMemberList(memberList);

        promotionProjectMemberList.forEach(promotionProjectMember -> {promotionProjectMember.setPromotionProject(newPromotionProject);});

        return promotionProjectRepository.save(newPromotionProject);
    }

    // 프로젝트 홍보 수정
    @Override
    @Transactional
    public PromotionProject updatePromotionProject(PromotionProjectRequestDTO.UpdatePromotionProjectDTO request, Long promotionProjectId) {

        PromotionProject existingPromotionProject = promotionProjectRepository.findById(promotionProjectId)
                .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_NOT_FOUND));

        existingPromotionProject.setTitle(request.getTitle());
        existingPromotionProject.setIntro(request.getInfo());
        existingPromotionProject.setDescription(request.getDescription());

        promotionProjectMemberRepository.deleteByPromotionProject(existingPromotionProject);

        List<Member> memberList = request.getPromotionProjectMember().stream()
                .map(memberId -> memberRepository.findById(memberId)
                        .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_ID_NOT_FOUND)))
                .collect(Collectors.toList());

        List<PromotionProjectMember> newMembers = PromotionProjectMemberConverter.toPromotionProjectMemberList(memberList);
        newMembers.forEach(promotionProjectMember -> {promotionProjectMember.setPromotionProject(existingPromotionProject);});

        existingPromotionProject.setPromotionProjectMemberList(newMembers);

        return promotionProjectRepository.save(existingPromotionProject);
    }

    // 프로젝트 홍보 삭제
    public Void deletePromotionProject(Long promotionProjectId) {
        PromotionProject existingPromotionProject = promotionProjectRepository.findById(promotionProjectId)
                .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_NOT_FOUND));

        promotionProjectRepository.deleteById(existingPromotionProject.getId());
        return null;
    }
}
