package com.partnerd.domain;

import com.partnerd.domain.Club;
import com.partnerd.domain.CollabPost;
import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.mapping.CollabPostCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CollabPostCategory> collabPostCategoryList = new ArrayList<>();

}
