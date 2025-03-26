package com.partnerd.mongoRepository.domain;

import com.partnerd.mongoRepository.domain.common.BaseEntity;
import com.partnerd.mongoRepository.domain.mapping.ProjectCategoryPrefer;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectCategory extends BaseEntity {

    // 포로젝트 역할 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 역할명
    @Column(nullable = false)
    private String name;

    //
    @OneToMany(mappedBy = "projectCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProjectCategoryPrefer> projectCategoryPreferList = new ArrayList<>();
}
