package com.partnerd.service.CommunityCommentService;

import com.partnerd.domain.CommunityComment;
import com.partnerd.web.dto.CommunityCommentDTO.CommunityCommentRequestDTO;
import com.partnerd.web.dto.CommunityCommentDTO.CommunityCommentResponseDTO;

import java.util.List;

public interface CommunityCommentService {

    // 커뮤니티 댓글 작성
    CommunityComment addCommunityComment(Long memberId, Long communityId, CommunityCommentRequestDTO.AddCommunityCommentDTO request);

    // 커뮤니티 대댓글 작성
    CommunityComment addChildCommunityComment(Long memberId, Long communityId, Long parentId, CommunityCommentRequestDTO.AddCommunityCommentDTO request);

    // 커뮤니티 댓글/대댓글 수정
    CommunityComment updateCommunityComment(Long memberId, Long commentId, CommunityCommentRequestDTO.AddCommunityCommentDTO request);

    // 커뮤니티 댓글/대댓글 삭제
    void deleteCommunityComment(Long memberId, Long commentId);

    // 커뮤니티 댓글 전체 조회
    List<CommunityCommentResponseDTO.GetCommunityCommentListResultDTO> getCommunityCommentList(Long communityId);

    // 커뮤니티 댓글 좋아요
    void communityCommentLikes(Long memberId, Long commentId);
}
