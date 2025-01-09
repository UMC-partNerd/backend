package com.partnerd.domain.mapping;

import com.partnerd.domain.common.BaseEntity;
import com.partnerd.domain.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubMembershipRequest extends BaseEntity {

    // 가입 요청 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 가입 요청 날짜
    @Column(nullable = false)
    private Date request_at;

    // 승인/거절 날짜
    private Date decision_at;

    // 요청 상태
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
