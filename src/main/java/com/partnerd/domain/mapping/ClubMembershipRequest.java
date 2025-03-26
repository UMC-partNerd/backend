package com.partnerd.domain.mapping;

import com.partnerd.domain.enums.RequestStatus;
import com.partnerd.domain.Club;
import com.partnerd.domain.Member;
import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubMembershipRequest extends BaseEntity {

    // 가입 요청 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 요청 상태
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    // 보낸 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 받은 동아리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    public void changeStatus(RequestStatus status) {
        if(this.status.equals(RequestStatus.PENDING)) {
            this.status = status;
        }
    }
}
