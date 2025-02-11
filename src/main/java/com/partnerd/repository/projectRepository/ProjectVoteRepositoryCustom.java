package com.partnerd.repository.projectRepository;


public interface ProjectVoteRepositoryCustom {
    
    // 프로젝트 홍보 투표하기 여부 확인
    boolean existsByMemberIdAndPromotionProjectId(Long memberId, Long promotionProjectId);
}
