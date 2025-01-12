package com.partnerd.converter.collabPostConverter;

import com.partnerd.domain.CollabPost;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;

import java.util.ArrayList;

public class CollabPostConverter {

    // 콜라보 글 생성 결과 DTO 변환
    public static CollabPost toCollabPost(CollabPostRequestDTO.RequestCollabPostDTO requestDTO) {

        return CollabPost.builder()
                .title(requestDTO.getTitle())
                .intro(requestDTO.getIntro())
                .description(requestDTO.getDescription())
                .open_date(requestDTO.getOpenDate())
                .close_date(requestDTO.getCloseDate())
                .collab_target(requestDTO.getCollabTarget())
                .start_date(requestDTO.getStartDate())
                .end_date(requestDTO.getEndDate())
                .event_mode(requestDTO.getEventMode())
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
