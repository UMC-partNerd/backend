package com.partnerd.web.dto.clubDTO;

import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ClubUpdateRequestDTO {
    private String name;
    private String intro;
    private List<ContactMethodDTO> contactMethod;
    private Long memberId;
    private Long categoryId;
    private MultipartFile bannerImage;  // 이미지 처리 미구현
    private MultipartFile profileImage;
}