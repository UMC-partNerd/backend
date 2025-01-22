package com.partnerd.web.dto.projectDTO;

import com.partnerd.domain.mapping.ProjectMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMemberDTO {
    private Long id;
    private String name;

    public ProjectMemberDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ProjectMemberDTO toProjectMemberDTO(ProjectMember projectMember) {
        return new ProjectMemberDTO(
                projectMember.getMember().getId(),
                projectMember.getMember().getName()
        );
    }

}
