package com.partnerd.domain.mapping;

import com.partnerd.domain.Project;
import com.partnerd.domain.ProjectCategory;
import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectCategoryPrefer extends BaseEntity {

    // 선호 프로젝트 카테고리 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 프로젝트 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    // 프로젝트 카테고리 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_category_id")
    private ProjectCategory projectCategory;
}
