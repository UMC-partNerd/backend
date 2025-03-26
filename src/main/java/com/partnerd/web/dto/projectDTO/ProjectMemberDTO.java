package com.partnerd.web.dto.projectDTO;

import com.partnerd.mongoRepository.domain.mapping.ProjectMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMemberDTO {
    private Long id;
    private String nickname;
    private String profileKeyName;

    public ProjectMemberDTO(Long id, String nickname, String profileKeyName) {
        this.id = id;
        this.nickname = nickname;
        this.profileKeyName = profileKeyName;
    }

    public static ProjectMemberDTO toProjectMemberDTO(ProjectMember projectMember) {
        return new ProjectMemberDTO(
                projectMember.getMember().getId(),
                projectMember.getMember().getNickname(),
                projectMember.getMember().getProfile_url()
        );
    }

}
