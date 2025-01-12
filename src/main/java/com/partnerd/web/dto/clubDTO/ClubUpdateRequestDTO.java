package com.partnerd.web.dto.clubDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubUpdateRequestDTO {
    private String name;
    private String intro;
    private String contact;
    private String category;
    // 추가적인 필드가 필요할 경우 여기에 추가
    //이미지는 아직 제외했습니다 이미지 업로드 관련파트
}
