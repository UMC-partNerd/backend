package com.partnerd.service.communityService;

import com.partnerd.domain.Community;
import com.partnerd.web.dto.CommunityDTO.CommunityRequestDTO;

public interface CommunityCommandService {

    Community addCommunity(Long memberId, CommunityRequestDTO.addRequestCommunityDTO requestDTO);

    Community modifyCommunity(Long memberId, Long communityId, CommunityRequestDTO.addRequestCommunityDTO requestDTO);

    void deleteCommunity(Long memberId, Long communityId);

    Community communityLikes(Long memberId, Long communityId);
}
