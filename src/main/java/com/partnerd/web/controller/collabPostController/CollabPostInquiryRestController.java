package com.partnerd.web.controller.collabPostController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CollabInquiryHandler;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.collabPostConverter.CollabInquiryConverter;
import com.partnerd.mongoRepository.domain.CollabInquiry;
import com.partnerd.mongoRepository.domain.enums.LikedAction;
import com.partnerd.service.collabPostService.CollabInquiryCommandService;
import com.partnerd.web.dto.collabDTO.request.CollabInquiryRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabInquiryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collabInquiry")
@RequiredArgsConstructor
public class CollabPostInquiryRestController {

    private final CollabInquiryCommandService collabInquiryCommandService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    @Operation(summary = "콜라보 문의글 작성 API",description = "콜라보 문의글 작성 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabInquiryResponseDTO.addCollabInquiryResponseDTO> addCollabInquiry (@RequestHeader("Authorization") String authorizationHeader,
                                                                                               @RequestBody CollabInquiryRequestDTO.addCollabInquiryDTO
                                                                                                           requestDTO) {


        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        CollabInquiry collabInquiry = collabInquiryCommandService.addCollabInquiry(requestDTO, memberId);

        return ApiResponse.onSuccess(CollabInquiryConverter.toCollabInquiryResultDTO(collabInquiry));
    }

    @PostMapping("/{parentId}")
    @Operation(summary = "콜라보 답글 작성 API",description = "콜라보 답글 작성 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabInquiryResponseDTO.addCollabInquiryResponseDTO> addChildCollabInquiry (@RequestHeader("Authorization") String authorizationHeader,
                                                                                                    @PathVariable(name = "parentId") Long parentId,
                                                                                                    @RequestBody CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO) {


        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        CollabInquiry collabInquiry = collabInquiryCommandService.addChildInquiry(parentId, requestDTO, memberId);

        return ApiResponse.onSuccess(CollabInquiryConverter.toChildCollabInquiryResultDTO(collabInquiry));
    }

    @PatchMapping("/{collabInquiryId}")
    @Operation(summary = "콜라보 문의글 및 답변 수정 API",description = "콜라보 문의글 및 답변 수정 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabInquiryResponseDTO.addCollabInquiryResponseDTO> ModifyCollabInquiry (@RequestHeader("Authorization") String authorizationHeader,
                                                                                                  @PathVariable(name = "collabInquiryId") Long collabInquiryId,
                                                                                                  @RequestParam String contents) {



        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        CollabInquiry collabInquiry = collabInquiryCommandService.modifyCollabInquiry(collabInquiryId, contents, memberId);

        CollabInquiryResponseDTO.addCollabInquiryResponseDTO responseDTO = null;
        if (collabInquiry.getParentInquiry() != null) {
            responseDTO= CollabInquiryConverter.toChildCollabInquiryResultDTO(collabInquiry);
        } else {
            responseDTO = CollabInquiryConverter.toCollabInquiryResultDTO(collabInquiry);
        }
        return ApiResponse.onSuccess(responseDTO);
    }


    @DeleteMapping("/{collabInquiryId}")
    @Operation(summary = "콜라보 문의글 삭제 API",description = "콜라보 문의글 삭제 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<Long> deleteCollabInquiry(@RequestHeader("Authorization") String authorizationHeader,
                                                 @PathVariable(name = "collabInquiryId") Long collabInquiryId) {

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        collabInquiryCommandService.deleteCollabInquiry(collabInquiryId, memberId);

        return ApiResponse.onSuccess(collabInquiryId);
    }

    @DeleteMapping("/{collabInquiryId}/reply")
    @Operation(summary = "콜라보 답변글 삭제 API",description = "콜라보 답변글 삭제 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<Long> deleteCollabChildInquiry(@RequestHeader("Authorization") String authorizationHeader,
                                                      @PathVariable(name = "collabInquiryId") Long collabInquiryId) {


        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        collabInquiryCommandService.deleteCollabChildInquiry(collabInquiryId, memberId);

        return ApiResponse.onSuccess(collabInquiryId);
    }

    @PatchMapping("/{collabInquiryId}/likes")
    @Operation(summary = "콜라보 문의글 및 답변글 좋아요/좋아요 취소 API",description = "콜라보 문의글 및 답변글 좋아요 개수 증가/감소 API입니다. " +
            "LikedAction 이 REMOVE 이면 좋아요 취소, ADD 이면 좋아요 추가입니다. ")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<Integer> addInquiryLikes(@RequestHeader("Authorization") String authorizationHeader,
                                                @PathVariable(name = "collabInquiryId") Long collabInquiryId,
                                                @RequestParam(name = "liked") LikedAction liked) {

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        Integer likes = 0;
        if (liked.equals(LikedAction.REMOVE)) {
            likes = collabInquiryCommandService.removeLike(collabInquiryId, memberId);
        } else if (liked.equals(LikedAction.ADD)) {
            likes = collabInquiryCommandService.addLike(collabInquiryId, memberId);
        } else {
            throw new CollabInquiryHandler(ErrorStatus._BAD_REQUEST);
        }

        return ApiResponse.onSuccess(likes);

    }




}
