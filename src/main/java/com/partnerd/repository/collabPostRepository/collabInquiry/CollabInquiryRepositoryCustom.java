package com.partnerd.repository.collabPostRepository.collabInquiry;

import com.partnerd.mongoRepository.domain.CollabInquiry;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CollabInquiryRepositoryCustom {
    Optional<CollabInquiry> findByIdWithMemberAndPost(@Param("id") Long id);
}

