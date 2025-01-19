package com.partnerd.converter.collabPostConverter;

import com.partnerd.domain.CollabPost;
import com.partnerd.web.dto.categoryDTO.CategoryDTO;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
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
    public static CollabPost toCollabPost(CollabPostRequestDTO.RequestCollabPostDTO requestDTO) {

        return CollabPost.builder()
                .title(requestDTO.getTitle())
                .intro(requestDTO.getIntro())
                .description(requestDTO.getDescription())
                .openDate(requestDTO.getOpenDate())
                .closeDate(requestDTO.getCloseDate())
                .collabTarget(requestDTO.getCollabTarget())
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .eventMode(requestDTO.getEventMode())
                .collabPostCategoryList(new ArrayList<>())
                .contactMethodList(new ArrayList<>())
                .build();

    }

    public static CollabPostResponseDTO.addCollabPostResultDTO toCollabPostResultDTO(CollabPost collabPost) {

        return CollabPostResponseDTO.addCollabPostResultDTO.builder()
                .collabPostId(collabPost.getId())
                .createdAt(collabPost.getCreatedAt())
                .build();
    }


}
