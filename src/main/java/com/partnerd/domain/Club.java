package com.partnerd.domain;

import com.partnerd.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String intro;

    @Column(nullable = true)
    private String profile; // 프로필 이미지 (null 가능)

    @Column(nullable = false)
    private Long views;

    @Column(nullable = false)
    private String contact_Method;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubActivity> activities = new ArrayList<>();

    // 클럽 정보를 업데이트하는 메서드
    public void update(String name, String intro, String contact_Method, String category) {
        this.name = name;
        this.intro = intro;
        this.contact_Method = contact_Method;
        /*this.category = category;*/
    }
}
