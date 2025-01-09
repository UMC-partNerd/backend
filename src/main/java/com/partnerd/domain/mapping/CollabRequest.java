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
public class CollabRequest extends BaseEntity {

    // 콜라보 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 요청 상태
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    
    // 요청 보낸 날짜
    @Column(nullable = false)
    private Date request_date;
}
