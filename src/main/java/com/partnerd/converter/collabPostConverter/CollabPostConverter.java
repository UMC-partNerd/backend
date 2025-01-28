package com.partnerd.converter.collabPostConverter;

import com.partnerd.domain.CollabPost;
import com.partnerd.domain.CollabPostImg;
import com.partnerd.domain.mapping.ClubMember;
import com.partnerd.web.dto.categoryDTO.CategoryDTO;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import com.partnerd.web.dto.contactMethodDTO.ContactMethodDTO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CollabPostConverter {


    public static CollabPostResponseDTO.CollabPostPreviewDTO collabPostPreviewDTO(CollabPost collabPost) {

        List<CategoryDTO> categoryDTOS =  collabPost.getCollabPostCategoryList().stream()
                .map(collabPostCategory -> {
                        CategoryDTO categoryDTO = CategoryDTO.builder()
                                .id(collabPostCategory.getCategory().getId())
                                .name(collabPostCategory.getCategory().getName())
                                .build();
                        return categoryDTO;
                }).collect(Collectors.toList());

        return CollabPostResponseDTO.CollabPostPreviewDTO.builder()
                .collabPostId(collabPost.getId())
                .title(collabPost.getTitle())
                .startDate(collabPost.getStartDate())
                .endDate(collabPost.getEndDate())
                .categoryDTOList(categoryDTOS)
                .build();
    }

    public static CollabPostResponseDTO.CollabPostPreviewListDTO collabPostPreviewListDTO(Page<CollabPost> collabPostPage) {
        List<CollabPostResponseDTO.CollabPostPreviewDTO> collabPostPreviewDTOList =
                    collabPostPage.stream().map(CollabPostConverter::collabPostPreviewDTO).collect(Collectors.toList());

        return CollabPostResponseDTO.CollabPostPreviewListDTO.builder()
                .collabPostPreviewDTOLList(collabPostPreviewDTOList)
                .listSize(collabPostPage.getSize())
                .totalPage(collabPostPage.getTotalPages())
                .totalElements(collabPostPage.getTotalElements())
                .isFirst(collabPostPage.isFirst())
                .isLast(collabPostPage.isLast())
                .build();
    }



    // 콜라보 글 생성 결과 DTO 변환
    public static CollabPost toCollabPost(CollabPostRequestDTO.RequestCollabPostDTO requestDTO, ClubMember clubMember) {

        return CollabPost.builder()
                .clubMember(clubMember)
                .title(requestDTO.getTitle())
                .intro(requestDTO.getIntro())
                .description(requestDTO.getDescription())
                .openDate(requestDTO.getOpenDate())
                .closeDate(requestDTO.getCloseDate())
                .collabTarget(requestDTO.getCollabTarget())
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .eventMode(requestDTO.getEventMode())
                .collabPostCategoryList(new LinkedHashSet<>())
                .contactMethodList(new LinkedHashSet<>())
                .collabPostImgList(new LinkedHashSet<>())
                .build();

    }

    public static CollabPostResponseDTO.addCollabPostResultDTO toCollabPostResultDTO(CollabPost collabPost) {

        return CollabPostResponseDTO.addCollabPostResultDTO.builder()
                .collabPostId(collabPost.getId())
                .createdAt(collabPost.getCreatedAt())
                .build();
    }


    public static CollabPostResponseDTO.CollabPostDetailDTO toCollabPostDetailDTO(CollabPost collabPost) {


        List<CategoryDTO> categoryDTOS =  collabPost.getCollabPostCategoryList().stream()
                .map(collabPostCategory -> {
                    CategoryDTO categoryDTO = CategoryDTO.builder()
                            .id(collabPostCategory.getCategory().getId())
                            .name(collabPostCategory.getCategory().getName())
                            .build();
                    return categoryDTO;
                }).collect(Collectors.toList());

        List<CollabPostResponseDTO.CollabInquiryDTO> collabInquiryDTOS =
                collabPost.getCollabInquiryList().stream()
                        .map(CollabPostResponseDTO.CollabInquiryDTO::fromEntity)
                        .collect(Collectors.toList());

        List<ContactMethodDTO> contactMethodDTOS =
                collabPost.getContactMethodList().stream()
                        .map(ContactMethodDTO::new)
                        .collect(Collectors.toList());


        String bannerKeyName = null;
        String mainKeyName = null;
        List<String> eventKeyNameList = new ArrayList<>();

        for (CollabPostImg collabPostImg : collabPost.getCollabPostImgList()) {

            switch (collabPostImg.getImageType()) {
                case BANNER:
                    bannerKeyName = collabPostImg.getKeyName();
                    break;
                case MAIN:
                    mainKeyName = collabPostImg.getKeyName();
                    break;
                case EVENT:
                    eventKeyNameList.add(collabPostImg.getKeyName());
                    break;
            }
        }
        return CollabPostResponseDTO.CollabPostDetailDTO.builder()
                .title(collabPost.getTitle())
                .intro(collabPost.getIntro())
                .openDate(collabPost.getOpenDate())
                .closeDate(collabPost.getCloseDate())
                .startDate(collabPost.getStartDate())
                .endDate(collabPost.getEndDate())
                .eventType(collabPost.getEventType().getName())
                .nickname(collabPost.getClubMember().getMember().getNickname())
                .eventMode(collabPost.getEventMode())
                .description(collabPost.getDescription())
                .CollabInquiryList(collabInquiryDTOS)
                .contactMethod(contactMethodDTOS)
                .collabTarget(collabPost.getCollabTarget())
                .categoryDTOList(categoryDTOS)
                .bannerKeyName(bannerKeyName)
                .mainKeyName(mainKeyName)
                .eventImgKeyNameList(eventKeyNameList)
                .build();
    }


    // 마이페이지 - 내가 쓴 콜라보레이션 모아보기
    public static CollabPostResponseDTO.MypageCollabPostPreviewListDTO toMyCollabPostsDTO(Long memberId, List<CollabPost> collabPosts) {
        List<CollabPostResponseDTO.MypageCollabPostPreviewDTO> collabPostPreviewDTOList = collabPosts.stream()
                .map(collabPost -> CollabPostResponseDTO.MypageCollabPostPreviewDTO.builder()
                        .collabPostId(collabPost.getId()) // 콜라보레이션 ID
                        .title(collabPost.getTitle()) // 콜라보레이션 제목
                        .description(collabPost.getDescription()) // 콜라보레이션 설명
                        .createdAt(collabPost.getCreatedAt()) // 생성 날짜
                        .updatedAt(collabPost.getUpdatedAt()) // 수정 날짜
                        .build())
                .toList();

        return CollabPostResponseDTO.MypageCollabPostPreviewListDTO.builder()
                .memberId(memberId) // 사용자 ID
                .mypageCollabPostPreviewDTOList(collabPostPreviewDTOList) // 변환된 콜라보레이션 DTO 리스트
                .build();
    }
}
