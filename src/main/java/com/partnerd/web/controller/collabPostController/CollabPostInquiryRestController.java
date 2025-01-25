package com.partnerd.web.controller.collabPostController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.converter.collabPostConverter.CollabInquiryConverter;
import com.partnerd.domain.CollabInquiry;
import com.partnerd.service.collabPostService.CollabInquiryCommandService;
import com.partnerd.service.collabPostService.CollabInquiryQueryService;
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
    private final CollabInquiryQueryService collabInquiryQueryService;

    @PostMapping("/register")
    @Operation(summary = "콜라보 문의글 작성 API",description = "콜라보 문의글 작성 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabInquiryResponseDTO.addCollabInquiryResponseDTO> addCollabInquiry (@RequestBody CollabInquiryRequestDTO.addCollabInquiryDTO
                                                                                              requestDTO) {

        CollabInquiry collabInquiry = collabInquiryCommandService.addCollabInquiry(requestDTO);

        return ApiResponse.onSuccess(CollabInquiryConverter.toCollabInquiryResultDTO(collabInquiry));
    }

    @PostMapping("/{parentId}")
    @Operation(summary = "콜라보 답글 작성 API",description = "콜라보 답글 작성 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabInquiryResponseDTO.addCollabInquiryResponseDTO> addChildCollabInquiry (@PathVariable(name = "parentId") Long parentId,
                                                                                                    @RequestBody CollabInquiryRequestDTO.addCollabInquiryDTO requestDTO) {

        CollabInquiry collabInquiry = collabInquiryCommandService.addChildInquiry(parentId, requestDTO);

        return ApiResponse.onSuccess(CollabInquiryConverter.toChildCollabInquiryResultDTO(collabInquiry));
    }

    @PatchMapping("/{collabInquiryId}")
    @Operation(summary = "콜라보 문의글 및 답변 수정 API",description = "콜라보 문의글 및 답변 수정 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabInquiryResponseDTO.addCollabInquiryResponseDTO> ModifyCollabInquiry (@PathVariable(name = "collabInquiryId") Long collabInquiryId,
                                                                                                  @RequestParam String contents) {

        CollabInquiry collabInquiry = collabInquiryCommandService.modifyCollabInquiry(collabInquiryId, contents);
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
    public ApiResponse<Long> deleteCollabInquiry(@PathVariable(name = "collabInquiryId") Long collabInquiryId) {

        collabInquiryCommandService.deleteCollabInquiry(collabInquiryId);

        return ApiResponse.onSuccess(collabInquiryId);
    }

    @DeleteMapping("/{collabInquiryId}/reply")
    @Operation(summary = "콜라보 답변글 삭제 API",description = "콜라보 답변글 삭제 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<Long> deleteCollabChildInquiry(@PathVariable(name = "collabInquiryId") Long collabInquiryId) {

        collabInquiryCommandService.deleteCollabChildInquiry(collabInquiryId);

        return ApiResponse.onSuccess(collabInquiryId);
    }

    @PatchMapping("/{collabInquiryId}/likes")
    @Operation(summary = "콜라보 문의글 및 답변글 좋아요/좋아요 취소 API",description = "콜라보 문의글 및 답변글 좋아요 개수 증가/감소 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<Integer> addInquiryLikes(@PathVariable(name = "collabInquiryId") Long collabInquiryId,
                                                @RequestParam(name = "liked") Integer liked) {

        Integer likes = 0;
        if (liked == 0) {
            likes = collabInquiryCommandService.removeLike(collabInquiryId);
        } else {
            likes = collabInquiryCommandService.addLike(collabInquiryId);
        }

        return ApiResponse.onSuccess(likes);

    }




}
