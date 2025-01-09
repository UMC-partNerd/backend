package com.partnerd.domain.mapping;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.enums.ActiveType;
import com.partnerd.domain.enums.ClubMemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubMember extends BaseEntity {

    // 동아리 멤버 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 동아리 권한
    @Enumerated(EnumType.STRING)
    private ClubMemberRole role;

    // 동아리 가입 날짜
    @Column(nullable = false)
    private Date joined_date;
    
    // 동아리 활동 여부
    @Enumerated(EnumType.STRING)
    private ActiveType status;

}
