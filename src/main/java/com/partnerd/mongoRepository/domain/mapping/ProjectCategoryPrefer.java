package com.partnerd.mongoRepository.domain.mapping;

import com.partnerd.mongoRepository.domain.Project;
import com.partnerd.mongoRepository.domain.ProjectCategory;
import com.partnerd.mongoRepository.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
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


    // setter (프로젝트 모집글 생성)
    public void setProject(Project project){
        if (this.project != null){
            project.getProjectCategoryPreferList().remove(this);
        }
        this.project = project;
        project.getProjectCategoryPreferList().add(this);
    }
}


