package com.partnerd.domain.mapping;


import com.partnerd.domain.Category;
import com.partnerd.domain.CollabPost;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CollabPostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collab_post_id")
    private CollabPost collabPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void setCollabPost(CollabPost collabPost) {
        if(this.collabPost != null) {
            this.collabPost.getCollabPostCategoryList().remove(this);
        }
        this.collabPost = collabPost;
        collabPost.getCollabPostCategoryList().add(this);
    }

    public void setCategory(Category category) {
        if (this.category != null) {
            this.category.getCollabPostCategoryList().remove(this);
        }
        this.category = category;
        category.getCollabPostCategoryList().add(this);
    }
}
