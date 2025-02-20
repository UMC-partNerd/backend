package com.partnerd.converter.communityConverter;

import com.partnerd.domain.Community;
import com.partnerd.domain.CommunityImage;
import com.partnerd.domain.mapping.CommunityLikes;
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

    public static CommunityResponseDTO.responseLikesDTO toLikesResultDTO (Community community) {
        return CommunityResponseDTO.responseLikesDTO.builder()
                .communityId(community.getId())
                .likes(community.getLikes())
                .build();
    }

    public static CommunityResponseDTO.CommunityPreviewDTO communityPreviewDTO (Community community) {
        return CommunityResponseDTO.CommunityPreviewDTO.builder()
                .id(community.getId())
                .title(community.getTitle())
                .content(community.getContent())
                .nickName(community.getMember().getNickname())
                .profileKeyName(community.getMember().getProfile_url())
                .occupation_of_interest(community.getMember().getOccupation_of_interest())
                .belong_to_club(community.getMember().getBelong_to_club())
                .createdAt(community.getCreatedAt())
                .likesCnt(community.getLikes())
                .commentCnt(community.getCommunityCommentList().size())
                .build();
    }
    public static CommunityResponseDTO.CommunityTop10PreviewDTO communityTop10PreviewDTO (Community community) {
        return CommunityResponseDTO.CommunityTop10PreviewDTO.builder()
                .id(community.getId())
                .title(community.getTitle())
                .nickName(community.getMember().getNickname())
                .occupation_of_interest(community.getMember().getOccupation_of_interest())
                .belong_to_club(community.getMember().getBelong_to_club())
                .build();
    }

    public static CommunityResponseDTO.CommunityPreviewListDTO communityPreviewListDTO (List<Community> communityList, boolean hasnext, Long nextCursor) {

        List<CommunityResponseDTO.CommunityPreviewDTO> communityPreviewDTOS = communityList.stream()
                .map(CommunityConverter::communityPreviewDTO).collect(Collectors.toList());

        return  CommunityResponseDTO.CommunityPreviewListDTO.builder()
                .communityList(communityPreviewDTOS)
                .hasNext(hasnext)
                .nextCursor(nextCursor)
                .build();

    }
}
