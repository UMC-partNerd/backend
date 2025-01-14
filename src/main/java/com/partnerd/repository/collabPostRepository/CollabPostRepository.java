package com.partnerd.repository.collabPostRepository;

import com.partnerd.domain.CollabPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollabPostRepository extends JpaRepository<CollabPost, Long>, CollabPostRepositoryCustom {


}
