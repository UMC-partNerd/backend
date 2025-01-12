package com.partnerd.web.controller.collabPostController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.converter.collabPostConverter.CollabPostConverter;
import com.partnerd.domain.CollabPost;
import com.partnerd.service.collabPostService.CollabPostCommandService;
import com.partnerd.service.collabPostService.CollabPostQueryService;
import com.partnerd.web.dto.collabDTO.CollabPostRequestDTO;
import com.partnerd.web.dto.collabDTO.CollabPostResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/collabPosts")
@RequiredArgsConstructor
public class CollabPostRestController {

    private final CollabPostCommandService collabPostCommandService;
    private final CollabPostQueryService collabPostQueryService;

    // 콜라보 글 생성
    @PostMapping("/")
    @Operation(summary = "콜라보 글 생성 API",description = "콜라보 글을 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabPostResponseDTO.addCollabPostResultDTO> addCollabPost(@RequestBody CollabPostRequestDTO.addCollabPostDTO requestDTO) {

        // 사용자가 동아리에 리더진인지 확인 후에 작성 가능 -> 추후에 해당 기능 추가
        CollabPost collabPost = collabPostCommandService.addCollabPost(requestDTO);

        return ApiResponse.onSuccess(CollabPostConverter.toCollabPostResultDTO(collabPost));
    }


    // 콜라보 글 수정


    // 콜라보 글 삭제




}
