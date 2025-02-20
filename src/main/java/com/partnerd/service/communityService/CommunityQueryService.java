package com.partnerd.service.communityService;

import com.partnerd.domain.Community;
import com.partnerd.web.dto.CommunityDTO.CommunityResponseDTO;

import java.util.List;

public interface CommunityQueryService {

    CommunityResponseDTO.CommunityPreviewListDTO getCommunityList(Long cursor, int size);
    List<CommunityResponseDTO.CommunityTop10PreviewDTO> getCommunityTop10List();

    // 마이페이지 - 내가 쓴 커뮤니티 모아보기
    List<Community> getMyCommunities(Long memberId);
}
