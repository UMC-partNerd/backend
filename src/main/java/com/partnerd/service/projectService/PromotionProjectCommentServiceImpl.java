package com.partnerd.service.projectService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.ProjectHandler;
import com.partnerd.converter.projectConverter.PromotionProjectCommentConverter;
import com.partnerd.domain.PromotionProjectComment;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.projectRepository.PromotionProjectCommentRepository;
import com.partnerd.repository.projectRepository.PromotionProjectRepository;
import com.partnerd.web.dto.projectDTO.PromotionProjectCommentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromotionProjectCommentServiceImpl implements PromotionProjectCommentService {

    private final PromotionProjectRepository promotionProjectRepository;
    private final PromotionProjectCommentRepository promotionProjectCommentRepository;
    private final MemberRepository memberRepository;

    // 프로젝트 홍보글 댓글 작성
    @Override
    @Transactional
    public PromotionProjectComment addPromotionProjectComment(Long memberId, Long projectId, PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request){

        PromotionProjectComment promotionProjectComment = PromotionProjectCommentConverter.toPromotionProjectComment(request);

        promotionProjectComment.setPromotionProject(promotionProjectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ProjectHandler(ErrorStatus.RECRUIT_PROJECT_NOT_FOUND)));

        promotionProjectComment.setMember(memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ProjectHandler(ErrorStatus.MEMBER_NOT_FOUND)));

        return promotionProjectCommentRepository.save(promotionProjectComment);
    }

}
