package com.partnerd.repository.projectRepository;


import com.partnerd.mongoRepository.domain.PromotionProjectComment;

import java.util.List;

public interface PromotionProjectCommentRepositoryCustom{
    // 프로젝트 홍보글 댓글 전체 조회
    List<PromotionProjectComment> findPromotionProjectCommentList(Long projectId);
}
