package com.partnerd.converter.communityConverter;

import com.partnerd.domain.Community;
import com.partnerd.domain.CommunityImage;
import com.partnerd.web.dto.CommunityDTO.CommunityResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CommunityConverter {

    public static CommunityResponseDTO.addResponseCommunityDTO toCommunityResultDTO (Community community) {

        List<String> communityImgKeyNameList = community.getCommunityImageList().stream()
                .map(CommunityImage::getKeyName)
                .collect(Collectors.toList());

        return CommunityResponseDTO.addResponseCommunityDTO.builder()
                .id(community.getId())
                .title(community.getTitle())
                .content(community.getContent())
                .communityImgKeyName(communityImgKeyNameList)
                .build();
    }

}
