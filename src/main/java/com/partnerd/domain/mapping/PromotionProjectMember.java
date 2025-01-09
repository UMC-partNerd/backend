package com.partnerd.domain.mapping;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.enums.ProjectMemberRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PromotionProjectMember extends BaseEntity {

    // 홍보 프로젝트 팀원 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 권한(역할)
    @Enumerated(EnumType.STRING)
    private ProjectMemberRole projectMemberRole;
}
