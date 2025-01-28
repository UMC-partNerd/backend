package com.partnerd.web.dto.projectDTO;

import lombok.Getter;
import lombok.Setter;

public class ProjectCommentRequestDTO {

    @Getter
    @Setter
    public static class AddProjectCommentDTO {
        private String contents;
    }
}
