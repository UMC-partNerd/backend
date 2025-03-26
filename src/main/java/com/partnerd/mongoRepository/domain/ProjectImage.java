package com.partnerd.mongoRepository.domain;

import com.partnerd.mongoRepository.domain.common.BaseEntity;
import com.partnerd.mongoRepository.domain.enums.ImageType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectImage extends BaseEntity {

    // 프로젝트 서비스 소개 사진 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_name", nullable = false)
    private String keyName;

    @Column(name = "image_type", nullable = false)
    private ImageType imageType;

    // 프로젝트 ID (FK)
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public void setProject(Project project){
        if(this.project != null){
            if (this.project.getProjectImageList() != null) {
                this.project.getProjectImageList().remove(this);
            }
        }
        this.project = project;
        if (this.project != null){
            if (this.project.getProjectImageList() == null){
                this.project.setProjectImageList(new HashSet<>());
            }
            this.project.getProjectImageList().add(this);
        }
    }
}
