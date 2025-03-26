package com.partnerd.service.clubService;

import com.partnerd.mongoRepository.domain.Club;
import com.partnerd.web.dto.clubDTO.*;
import com.partnerd.web.dto.memberDTO.MemberNickNameSearchDTO;

import java.util.List;

public interface ClubService {
    ClubRegisterResponseDTO registerClub(ClubRegisterRequestDTO dto, Long memberId);
    void deleteClub(Long clubId, Long memberId);

    ClubUpdateResponseDTO updateClub(Long clubId, ClubUpdateRequestDTO dto, Long memberId);

    List<ClubDTO> getClubs(Integer page, String sort, Long categoryId);

    // 파트너드 상세조회 (팀페이지)
    ClubDetailResponseDTO findClubDetails(Long clubId, Long memberId);

    //멤버닉네임조회 (파트너드 등록시 필요)
    List<MemberNickNameSearchDTO> searchMembersByNickname(String nickname);

    // 파트너드 목록 조회(마이페이지)
    List<Club> getClubsByRole(Long memberId);
}