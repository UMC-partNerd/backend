package com.partnerd.mongoRepository.domain;

import com.partnerd.mongoRepository.domain.enums.ImageType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CollabPostImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_name", nullable = false)
    private String keyName;

    @Column(name = "image_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @ManyToOne
    @JoinColumn(name = "collab_post_id")
    private CollabPost collabPost;

    public void setCollabPost(CollabPost collabPost) {
        if (this.collabPost != null) {
            this.collabPost.getCollabPostImgList().remove(this);
        }
        this.collabPost = collabPost;
        this.collabPost.getCollabPostImgList().add(this);
    }

}
