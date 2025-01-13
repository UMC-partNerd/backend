package com.partnerd.web.dto.clubDTO;


import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ClubRegisterRequestDTO {
    private String name;
    private String intro;
    private List<ContactMethodDTO> contactMethod;
    private int memberId;
    private int categoryId;
    private String activities;
    private MultipartFile bannerImage;  //이미지로직 아직 미구현
    private MultipartFile profileImage;
    private List<MultipartFile> activityImages;
}

