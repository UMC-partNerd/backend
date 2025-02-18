package com.partnerd.service.communityService;

import com.partnerd.web.dto.CommunityDTO.CommunityResponseDTO;

import java.util.List;

public interface CommunityQueryService {

    CommunityResponseDTO.CommunityPreviewListDTO getCommunityList(Long cursor, int size);
    List<CommunityResponseDTO.CommunityTop10PreviewDTO> getCommunityTop10List();

}
