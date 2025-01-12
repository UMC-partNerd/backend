package com.partnerd.web.dto.clubDTO;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ClubRegisterRequestDTO {
    private String name;
    private String intro;
    private String contact;
    private String category;
    private String activities;
    private List<String> memberNames;
    private MultipartFile bannerImage;  //이미지로직 아직 미구현
    private MultipartFile profileImage;
    private List<MultipartFile> activityImages;
}

