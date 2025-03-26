package com.partnerd.domain;

import com.partnerd.domain.mapping.CollabPostCategory;
import com.partnerd.domain.common.BaseEntity;
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

    @Setter
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CollabPostCategory> collabPostCategoryList = new ArrayList<>();

    // 단일 카테고리 연결된 클럽들
    @OneToMany(mappedBy = "category")
    private List<Club> clubs = new ArrayList<>();



}
