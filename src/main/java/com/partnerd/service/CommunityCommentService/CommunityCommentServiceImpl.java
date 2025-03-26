package com.partnerd.service.CommunityCommentService;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CommunityCommentHandler;
import com.partnerd.converter.CommunityCommentConverter;
import com.partnerd.mongoRepository.domain.CommunityComment;
import com.partnerd.mongoRepository.domain.Member;
import com.partnerd.mongoRepository.domain.mapping.CommunityCommentLikes;
import com.partnerd.repository.communityCommentRepository.CommunityCommentLikesRepository;
import com.partnerd.repository.communityCommentRepository.CommunityCommentRepository;
import com.partnerd.repository.communityRepository.CommunityRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.web.dto.CommunityCommentDTO.CommunityCommentRequestDTO;
import com.partnerd.web.dto.CommunityCommentDTO.CommunityCommentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityCommentServiceImpl implements CommunityCommentService {

    private final CommunityRepository communityRepository;
    private final CommunityCommentRepository communityCommentRepository;
    private final MemberRepository memberRepository;
    private final CommunityCommentLikesRepository communityCommentLikesRepository;

    // 커뮤니티 댓글 작성
    @Override
    @Transactional
    public CommunityComment addCommunityComment(Long memberId, Long communityId, CommunityCommentRequestDTO.AddCommunityCommentDTO request){

        CommunityComment communityComment = CommunityCommentConverter.toCommunityComment(request);

        communityComment.setCommunity(communityRepository.findById(communityId)
                .orElseThrow(() ->
                        new CommunityCommentHandler(ErrorStatus.COMMUNITY_NOT_FOUND)));

        communityComment.setMember(memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new CommunityCommentHandler(ErrorStatus.MEMBER_NOT_FOUND)));

        communityComment.setLikes(0L);

        return communityCommentRepository.save(communityComment);
    }

    // 커뮤니티 대댓글 작성
    @Override
    @Transactional
    public CommunityComment addChildCommunityComment(Long memberId, Long communityId, Long parentId, CommunityCommentRequestDTO.AddCommunityCommentDTO request){

        CommunityComment communityComment = CommunityCommentConverter.toCommunityComment(request);

        if (parentId != null) {
            CommunityComment parentComment = communityCommentRepository.findById(parentId).orElseThrow(() ->
                    new CommunityCommentHandler(ErrorStatus.PARENT_COMMUNITY_COMMENT_NOT_FOUND));

            communityComment.addParentComment(parentComment);

            communityComment.setCommunity(communityRepository.findById(communityId)
                    .orElseThrow(() ->
                            new CommunityCommentHandler(ErrorStatus.COMMUNITY_NOT_FOUND)));

            communityComment.setMember(memberRepository.findById(memberId)
                    .orElseThrow(() ->
                            new CommunityCommentHandler(ErrorStatus.MEMBER_NOT_FOUND)));
        } else {
            throw new CommunityCommentHandler(ErrorStatus.COMMUNITY_NOT_FOUND);
        }

        communityComment.setLikes(0L);
        return communityCommentRepository.save(communityComment);
    }

    // 커뮤니티 댓글/대댓글 수정
    @Override
    @Transactional
    public CommunityComment updateCommunityComment(Long memberId, Long commentId, CommunityCommentRequestDTO.AddCommunityCommentDTO request){

        memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new CommunityCommentHandler(ErrorStatus.MEMBER_NOT_FOUND));

        CommunityComment communityComment = communityCommentRepository.findById(commentId)
                .orElseThrow(() ->
                        new CommunityCommentHandler(ErrorStatus.COMMUNITY_COMMENT_NOT_FOUND));

        // 작성자 검증
        if (!communityComment.getMember().getId().equals(memberId))
            throw new CommunityCommentHandler(ErrorStatus.COMMUNITY_COMMENT_NOT_AUTHOR);

        communityComment.setContents(request.getContents());

        return communityCommentRepository.save(communityComment);
    }

    // 커뮤니티 댓글/대댓글 삭제
    @Override
    @Transactional
    public void deleteCommunityComment(Long memberId, Long commentId){
        memberRepository.findById(memberId)
                .orElseThrow(() -> new CommunityCommentHandler(ErrorStatus.MEMBER_NOT_FOUND));

        CommunityComment communityComment = communityCommentRepository.findById(commentId)
                .orElseThrow(() -> new CommunityCommentHandler(ErrorStatus.COMMUNITY_COMMENT_NOT_FOUND));

        // 작성자 검증
        if (!communityComment.getMember().getId().equals(memberId))
            throw new CommunityCommentHandler(ErrorStatus.COMMUNITY_COMMENT_NOT_AUTHOR);

        CommunityComment parentComment = communityComment.getParentComment();

        if (parentComment == null){  // 부모 댓글일때
            if (!communityComment.getChildren().isEmpty()){
                communityComment.changeToDeleted();
            } else {
                communityCommentRepository.delete(communityComment);
            }
        } else {    // 자식 댓글일 때
            if (parentComment.getIsDeleted() && communityComment.getParentComment().getChildren().size() == 1){
                communityCommentRepository.delete(communityComment.getParentComment());
            } else {
                parentComment.getChildren().remove(communityComment);
                communityCommentRepository.delete(communityComment);
            }
        }
    }

    // 커뮤니티 댓글 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<CommunityCommentResponseDTO.GetCommunityCommentListResultDTO> getCommunityCommentList(Long communityId){
        List<CommunityComment> communityCommentList = communityCommentRepository.findCommunityCommentList(communityId);

        return communityCommentList.stream()
                .map(CommunityCommentConverter::toGetCommunityCommentListResultDTO)
                .toList();
    }

    // 커뮤니티 댓글 좋아요
    @Override
    public void communityCommentLikes(Long memberId, Long commentId){
        boolean exists = communityCommentLikesRepository.existsByMemberIdAndCommunityCommentId(memberId, commentId);
        CommunityComment comment = communityCommentRepository.findById(commentId)
                .orElseThrow(() -> new CommunityCommentHandler(ErrorStatus.COMMUNITY_COMMENT_NOT_FOUND));


        if (exists) {
            // 좋아요 취소
            communityCommentLikesRepository.deleteByMemberIdAndCommunityCommentId(memberId, commentId);
            comment.setLikes(comment.getLikes() - 1);
        } else {
            // 좋아요 추가
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new CommunityCommentHandler(ErrorStatus.MEMBER_NOT_FOUND));

            CommunityCommentLikes communityLikes = CommunityCommentLikes.builder()
                    .member(member)
                    .communityComment(comment)
                    .build();

            communityCommentLikesRepository.save(communityLikes);
            comment.setLikes(comment.getLikes() + 1);
        }

        communityCommentRepository.save(comment);
    }
}
