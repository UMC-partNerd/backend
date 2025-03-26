package com.partnerd.service.communityService.impl;

import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CommunityHandler;
import com.partnerd.mongoRepository.domain.Community;
import com.partnerd.mongoRepository.domain.CommunityImage;
import com.partnerd.mongoRepository.domain.Member;
import com.partnerd.mongoRepository.domain.mapping.CommunityLikes;
import com.partnerd.repository.communityRepository.CommunityImgRepository;
import com.partnerd.repository.communityRepository.CommunityLikesRepository;
import com.partnerd.repository.communityRepository.CommunityRepository;
import com.partnerd.repository.memberRepository.MemberRepository;
import com.partnerd.service.communityService.CommunityCommandService;
import com.partnerd.web.dto.CommunityDTO.CommunityRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityCommandServiceImpl implements CommunityCommandService {

    private final CommunityRepository communityRepository;
    private final CommunityImgRepository communityImgRepository;
    private final CommunityLikesRepository communityLikesRepository;
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
                .communityImageList(new LinkedHashSet<>())
                .build();

        community.setMember(member);

        if (requestDTO.getCommunityImgKeyName() != null) {
           requestDTO.getCommunityImgKeyName().forEach((keyName -> {
                        CommunityImage communityImage = CommunityImage.builder()
                                .keyName(keyName)
                                .build();
                        communityImage.setCommunity(community);}));
        }

        return communityRepository.save(community);
    }

    @Override
    @Transactional
    public Community modifyCommunity(Long memberId, Long communityId, CommunityRequestDTO.addRequestCommunityDTO requestDTO) {

        List<CommunityImage> ImgToDelete = new ArrayList<>();
        List<CommunityImage> ImgToAdd = new ArrayList<>();

        // 커뮤니티 글이 존재하는지,해당 글에 작성자인지 확인
        Community community = communityRepository.findByIdWithMemebr(communityId).orElseThrow(() ->
            new CommunityHandler(ErrorStatus.COMMUNITY_NOT_FOUND));
        List<CommunityImage> CommunityImgListCopy = new ArrayList<>(community.getCommunityImageList());

        community.validatorAuthor(memberId);

        // 수정 시 이미지를 모두 지웠을 때, 저장된 이미지가 있으면 모두 삭제.
        if (requestDTO.getCommunityImgKeyName() == null) {
            if (CommunityImgListCopy != null) {
                ImgToDelete.addAll(community.getCommunityImageList());
            }
        }
        // 수정한 이미지들 중 원래 저장된 이미지와 일치하는게 없으면, 삭제할 이미지 리스트에 추가
        CommunityImgListCopy.forEach(communityImg -> {
                    if (!requestDTO.getCommunityImgKeyName().contains(communityImg.getKeyName())) {
                        ImgToDelete.add(communityImg);
                    }
                });

        // 수정한 이미지들 중 기존에 있던 이미지와 다른 새로운 이미지가 있으면, 저장할 이미지 리스트에 추가
        requestDTO.getCommunityImgKeyName().forEach(communityImageKeyName -> {
                    boolean exists = CommunityImgListCopy.stream()
                            .anyMatch(communityImage -> communityImage.getKeyName().equals(communityImageKeyName));

                    if (!exists) {
                        CommunityImage newCommunityImg = CommunityImage.builder()
                                .keyName(communityImageKeyName)
                                .build();
                        newCommunityImg.setCommunity(community);
                        ImgToAdd.add(newCommunityImg);
                    }
                });

        communityImgRepository.deleteAll(ImgToDelete);
        community.getCommunityImageList().removeAll(ImgToDelete);

        communityImgRepository.saveAll(ImgToAdd);
        community.getCommunityImageList().addAll(ImgToAdd);

        community.updateCommunity(requestDTO.getTitle(), requestDTO.getContent());

        return communityRepository.save(community);
    }

    @Override
    @Transactional
    public void deleteCommunity(Long memberId, Long communityId) {
        Community community = communityRepository.findById(communityId).orElseThrow(()
            -> new CommunityHandler(ErrorStatus.COMMUNITY_NOT_FOUND));

        community.validatorAuthor(memberId);

        communityRepository.delete(community);
    }

    @Override
    @Transactional
    public Community communityLikes(Long memberId, Long communityId) {

        CommunityLikes communityLikes = communityLikesRepository.findByCommunityAndMember(communityId, memberId);
        Community community = communityRepository.findById(communityId).orElseThrow(() ->
                new CommunityHandler(ErrorStatus.COMMUNITY_NOT_FOUND));

        // 좋아요
        if(communityLikes == null) {
            Member member = memberRepository.findById(memberId).orElseThrow(() ->
                    new CommunityHandler(ErrorStatus.MEMBER_NOT_FOUND));

            CommunityLikes addLikes = CommunityLikes.builder()
                    .member(member)
                    .community(community)
                    .build();

            community.addLikes();
            communityLikesRepository.save(addLikes);
        } else { // 좋아요 취소
            community.removeLikes();
            communityLikesRepository.delete(communityLikes);
        }

        return communityRepository.save(community);

    }

}
