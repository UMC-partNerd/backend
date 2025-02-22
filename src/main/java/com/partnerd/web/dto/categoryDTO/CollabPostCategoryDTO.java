package com.partnerd.web.dto.categoryDTO;

import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollabPostCategoryDTO {
    private Long collabPostId;
    private Long id;
    private String name;
}
