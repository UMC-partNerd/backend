package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.CollabInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollabInquiryRepository extends JpaRepository<CollabInquiry, Long> {
}
