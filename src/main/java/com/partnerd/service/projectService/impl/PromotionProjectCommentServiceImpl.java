package com.partnerd.service.projectService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.PromotionProjectHandler;
import com.partnerd.converter.projectConverter.PromotionProjectCommentConverter;
import com.partnerd.domain.Member;
import com.partnerd.domain.PromotionProjectComment;
import com.partnerd.domain.mapping.ProjectLikes;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.repository.projectRepository.projectLikes.ProjectLikesRepository;
import com.partnerd.repository.projectRepository.promotionProjectComment.PromotionProjectCommentRepository;
import com.partnerd.repository.projectRepository.promotionProject.PromotionProjectRepository;
import com.partnerd.service.projectService.PromotionProjectCommentService;
import com.partnerd.web.dto.projectDTO.PromotionProjectCommentRequestDTO;
import com.partnerd.web.dto.projectDTO.PromotionProjectCommentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionProjectCommentServiceImpl implements PromotionProjectCommentService {

    private final PromotionProjectRepository promotionProjectRepository;
    private final PromotionProjectCommentRepository promotionProjectCommentRepository;
    private final MemberRepository memberRepository;
    private final ProjectLikesRepository projectLikesRepository;

    // 프로젝트 홍보글 댓글 작성
    @Override
    @Transactional
    public PromotionProjectComment addPromotionProjectComment(Long memberId, Long projectId, PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request){

        PromotionProjectComment promotionProjectComment = PromotionProjectCommentConverter.toPromotionProjectComment(request);

        promotionProjectComment.setPromotionProject(promotionProjectRepository.findById(projectId)
                .orElseThrow(() ->
                        new PromotionProjectHandler(ErrorStatus.RECRUIT_PROJECT_NOT_FOUND)));

        promotionProjectComment.setMember(memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new PromotionProjectHandler(ErrorStatus.MEMBER_NOT_FOUND)));

        promotionProjectComment.setLikes(0L);

        return promotionProjectCommentRepository.save(promotionProjectComment);
    }

    // 프로젝트 홍보글 대댓글 작성
    @Override
    @Transactional
    public PromotionProjectComment addChildPromotionProjectComment(Long memberId, Long projectId, Long parentId, PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request){

        PromotionProjectComment promotionProjectComment = PromotionProjectCommentConverter.toPromotionProjectComment(request);

        if (parentId != null) {
            PromotionProjectComment parentComment = promotionProjectCommentRepository.findById(parentId).orElseThrow(() ->
                    new PromotionProjectHandler(ErrorStatus.PROMOTION_PARENT_PROJECT_COMMENT_NOT_FOUND));

            promotionProjectComment.addParentComment(parentComment);

            promotionProjectComment.setPromotionProject(promotionProjectRepository.findById(projectId)
                    .orElseThrow(() ->
                            new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_NOT_FOUND)));

            promotionProjectComment.setMember(memberRepository.findById(memberId)
                    .orElseThrow(() ->
                            new PromotionProjectHandler(ErrorStatus.MEMBER_NOT_FOUND)));
        } else {
            throw new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_ID_NOT_FOUND);
        }

        promotionProjectComment.setLikes(0L);

        return promotionProjectCommentRepository.save(promotionProjectComment);
    }

    // 프로젝트 홍보글 댓글/대댓글 수정
    @Override
    @Transactional
    public PromotionProjectComment updatePromotionProjectComment(Long memberId, Long commentId, PromotionProjectCommentRequestDTO.AddPromotionProjectCommentDTO request){

        memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new PromotionProjectHandler(ErrorStatus.MEMBER_NOT_FOUND));

        PromotionProjectComment promotionProjectComment = promotionProjectCommentRepository.findById(commentId)
                .orElseThrow(() ->
                        new PromotionProjectHandler(ErrorStatus.RECRUIT_PROJECT_COMMENT_NOT_FOUND));

        // 작성자 검증
        if (!promotionProjectComment.getMember().getId().equals(memberId))
            throw new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_COMMENT_NOT_AUTHOR);

        promotionProjectComment.setContents(request.getContents());

        return promotionProjectCommentRepository.save(promotionProjectComment);
    }

    // 프로젝트 홍보글 댓글/대댓글 삭제
    @Override
    @Transactional
    public void deletePromotionProjectComment(Long memberId, Long commentId){
        memberRepository.findById(memberId)
                .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.MEMBER_NOT_FOUND));

        PromotionProjectComment promotionProjectComment = promotionProjectCommentRepository.findById(commentId)
                .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.RECRUIT_PROJECT_COMMENT_NOT_FOUND));

        // 작성자 검증
        if (!promotionProjectComment.getMember().getId().equals(memberId))
            throw new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_COMMENT_NOT_AUTHOR);

        PromotionProjectComment parentComment = promotionProjectComment.getParentComment();

        if (parentComment == null){  // 부모 댓글일때
            if (!promotionProjectComment.getChildren().isEmpty()){
                promotionProjectComment.changeToDeleted();
            } else {
                promotionProjectCommentRepository.delete(promotionProjectComment);
            }
        } else {    // 자식 댓글일 때
            if (parentComment.getIsDeleted() && promotionProjectComment.getParentComment().getChildren().size() == 1){
                promotionProjectCommentRepository.delete(promotionProjectComment.getParentComment());
            } else {
                parentComment.getChildren().remove(promotionProjectComment);
                promotionProjectCommentRepository.delete(promotionProjectComment);
            }
        }
    }

    // 프로젝트 홍보글 댓글 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<PromotionProjectCommentResponseDTO.GetPromotionProjectCommentListResultDTO> getPromotionProjectCommentList(Long projectId){
        List<PromotionProjectComment> promotionProjectCommentList = promotionProjectCommentRepository.findPromotionProjectCommentList(projectId);

        return promotionProjectCommentList.stream()
                .map(PromotionProjectCommentConverter::toGetPromotionProjectCommentListResultDTO)
                .toList();
    }

    // 프로젝트 홍보 댓글 좋아요
    @Override
    public void projectLikes(Long memberId, Long commentId){

        boolean exists = projectLikesRepository.existsByMemberIdAndPromotionProjectCommentId(memberId, commentId);

        PromotionProjectComment comment = promotionProjectCommentRepository.findById(commentId)
                .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.PROMOTION_PROJECT_COMMENT_NOT_FOUND));

        if (exists) {
            // 좋아요 취소
            projectLikesRepository.deleteByMemberIdAndPromotionProjectCommentId(memberId, commentId);
            comment.setLikes(comment.getLikes() - 1);
        } else {
            // 좋아요 추가
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new PromotionProjectHandler(ErrorStatus.MEMBER_NOT_FOUND));

            ProjectLikes projectLikes = ProjectLikes.builder()
                    .member(member)
                    .promotionProjectComment(comment)
                    .build();

            projectLikesRepository.save(projectLikes);
            comment.setLikes(comment.getLikes() + 1);
        }
        promotionProjectCommentRepository.save(comment);
    }
}
