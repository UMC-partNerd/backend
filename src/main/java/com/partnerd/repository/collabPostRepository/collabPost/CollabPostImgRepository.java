package com.partnerd.repository.collabPostRepository.collabPost;

import com.partnerd.domain.CollabPostImg;
import com.partnerd.domain.enums.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CollabPostImgRepository extends JpaRepository<CollabPostImg, Long> {

    Set<CollabPostImg> findByCollabPost_id(Long collabPostId);
    CollabPostImg findByImageType(ImageType imageType);

}
