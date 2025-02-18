/*
package com.partnerd.web.controller.communityController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CollabPostHandler;
import com.partnerd.converter.collabPostConverter.CollabPostConverter;
import com.partnerd.domain.CollabPost;
import com.partnerd.service.communityService.CommuityCommandService;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityRestController {


    private final CommuityCommandService commuityCommandService;

    // 커뮤니티 글 생성
    @PostMapping
    @Operation(summary = "커뮤니티 글 생성 API",description = "커뮤니티 글을 생성하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabPostResponseDTO.addCollabPostResultDTO> addCollabPost(@RequestHeader("Authorization") String authorizationHeader,
                                                                                   @RequestBody CollabPostRequestDTO.RequestCollabPostDTO requestDTO) {

        if (requestDTO.getBannerKeyName() == null || requestDTO.getMainKeyName() == null) {
            throw new CollabPostHandler(ErrorStatus.COLLAB_POST_BAD_REQUEST);
        }

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        CollabPost collabPost = collabPostCommandService.addCollabPost(requestDTO, memberId);

        return ApiResponse.onSuccess(CollabPostConverter.toCollabPostResultDTO(collabPost));
    }





}
*/
