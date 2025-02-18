package com.partnerd.service.communityService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CommunityHandler;
import com.partnerd.domain.Community;
import com.partnerd.domain.CommunityImage;
import com.partnerd.domain.Member;
import com.partnerd.repository.communityRepository.CommnunityRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.service.communityService.CommunityCommandService;
import com.partnerd.web.dto.CommunityDTO.CommunityRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CommunityCommandServiceImpl implements CommunityCommandService {

    private final CommnunityRepository commnunityRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Community addCommunity(Long memberId, CommunityRequestDTO.addRequestCommunityDTO requestDTO) {

        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new CommunityHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Community community = Community.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .likes(0)
                .communityImageList(new ArrayList<>())
                .build();

        community.setMember(member);

        if (requestDTO.getCommunityImgKeyName() != null) {
           requestDTO.getCommunityImgKeyName().forEach((keyName -> {
                        CommunityImage communityImage = CommunityImage.builder()
                                .keyName(keyName)
                                .build();
                        communityImage.setCommunity(community);}));
        }

        return commnunityRepository.save(community);
    }



}
