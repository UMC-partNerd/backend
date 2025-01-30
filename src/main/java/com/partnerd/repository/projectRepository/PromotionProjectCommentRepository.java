package com.partnerd.repository.projectRepository;

import com.partnerd.domain.Member;
import com.partnerd.domain.PromotionProjectComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionProjectCommentRepository extends JpaRepository<PromotionProjectComment,Long>, PromotionProjectCommentRepositoryCustom {
    Optional<PromotionProjectComment> findByIdAndMember(Long commentId, Member member);
}
