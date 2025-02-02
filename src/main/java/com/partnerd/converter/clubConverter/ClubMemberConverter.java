package com.partnerd.converter.clubConverter;

import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.web.dto.clubDTO.*;

public class ClubMemberConverter {

    // 파트너드(동아리) 리더 권한 위임
    public static ClubMemberResponseDTO.ClubChangeLeaderDTO changeClubLeaderDTO(ClubMember newLeader) {
        return ClubMemberResponseDTO.ClubChangeLeaderDTO.builder()
                .newLeaderId(newLeader.getMember().getId())
                .clubId(newLeader.getClub().getId())
                .updatedAt(newLeader.getUpdatedAt())
                .build();
    }

    // 파트너드(동아리) 리더 권한 위임
    public static ClubMemberResponseDTO.ClubChangeActiveDTO changeMemberActviceDTO(ClubMember clubMember) {
        return ClubMemberResponseDTO.ClubChangeActiveDTO.builder()
                .memberId(clubMember.getMember().getId())
                .clubId(clubMember.getClub().getId())
                .status(clubMember.getStatus())
                .updatedAt(clubMember.getUpdatedAt())
                .build();
    }
}
