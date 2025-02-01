package com.partnerd.web.dto.projectDTO;

import com.partnerd.domain.mapping.ProjectMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMemberDTO {
    private Long id;
    private String nickname;
    private String profileImg;

    public ProjectMemberDTO(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.profileImg = null;
    }

    public static ProjectMemberDTO toProjectMemberDTO(ProjectMember projectMember) {
        return new ProjectMemberDTO(
                projectMember.getMember().getId(),
                projectMember.getMember().getNickname()
        );
    }

}
