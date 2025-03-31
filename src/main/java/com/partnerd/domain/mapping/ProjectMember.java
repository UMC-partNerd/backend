package com.partnerd.domain.mapping;

import com.partnerd.domain.Member;
import com.partnerd.domain.Project;
import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "project_member")
public class ProjectMember extends BaseEntity {

    // 프로젝트 팀원 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 프로젝트 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    // 사용자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // setter (프로젝트 모집글 생성)
    public void setProject(Project project){
        if (this.project != null){
            project.getProjectMemberList().remove(this);
        }
        this.project = project;
        project.getProjectMemberList().add(this);
    }
}
