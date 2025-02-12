package com.partnerd.web.dto.clubDTO;

import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClubDetailResponseDTO {
    private Long clubId;
    private String name;
    private String category;
    private String intro;
    private String profileImage;  //프로필 키네임
    private String bannerImage;   // 배너 키네임
    private List<ContactMethodDTO> contactMethod;
    private ClubActivityDTO activity;
    private List<ClubDetailMemberDTO> leaderMembers;
    private List<ClubDetailMemberDTO> generalMembers;
    private List<ClubDetailCollabDTO> collabPosts; //  최대 5개
    private boolean isLeader;
}