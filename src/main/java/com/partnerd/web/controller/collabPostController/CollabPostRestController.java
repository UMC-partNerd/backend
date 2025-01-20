package com.partnerd.web.controller.collabPostController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.converter.collabPostConverter.CollabPostConverter;
import com.partnerd.domain.CollabPost;
import com.partnerd.service.collabPostService.CollabPostCommandService;
import com.partnerd.service.collabPostService.CollabPostQueryService;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiResponse<CollabPostResponseDTO.addCollabPostResultDTO> addCollabPost(@RequestBody CollabPostRequestDTO.RequestCollabPostDTO requestDTO) {

        // 사용자가 동아리에 리더진인지 확인 후에 작성 가능 -> 추후에 해당 기능 추가
        CollabPost collabPost = collabPostCommandService.addCollabPost(requestDTO);

        return ApiResponse.onSuccess(CollabPostConverter.toCollabPostResultDTO(collabPost));
    }


    // 콜라보 글 수정
    @PatchMapping("/{collabPostId}")
    @Operation(summary = "콜라보 글 수정 API",description = "콜라보 글을 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabPostResponseDTO.addCollabPostResultDTO> modifyCollabPost( @PathVariable Long collabPostId, @RequestBody CollabPostRequestDTO.RequestCollabPostDTO requestDTO) {

        CollabPost collabPost = collabPostCommandService.modifyCollabPost(collabPostId, requestDTO);
        return ApiResponse.onSuccess(CollabPostConverter.toCollabPostResultDTO(collabPost));
    }

    // 콜라보 글 삭제
    @DeleteMapping("/{collabPostId}")
    @Operation(summary = "콜라보 글 삭제 API",description = "콜라보 글을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<Long> deleteCollabPost(@PathVariable(name = "collabPostId") Long collabPostId) {

        collabPostCommandService.deleteCollabPost(collabPostId);
        return ApiResponse.onSuccess(collabPostId);
    }

    // 콜라보 글 전체 조회 (최신순)
    @GetMapping("/")
    @Operation(summary = "콜라보 글 전체 조회 API (마감순, 최신순) ",description = "콜라보 글 전체 조회 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabPostResponseDTO.CollabPostPreviewListDTO> getCollaboPostList(@RequestParam(name = "page") Integer page,
                                                                                          @RequestParam(defaultValue = "endDate") String sortBy) {

        Page<CollabPost> collabPostPage = collabPostQueryService.getCollabPostList(page, sortBy);

        return ApiResponse.onSuccess(CollabPostConverter.collabPostPreviewListDTO(collabPostPage));
    }


    // 카테고리 별 콜라보 글 조회
    @GetMapping("/categories")
    @Operation(summary = "콜라보 글 카테고리 별 조회 API (마감순, 최신순) ",description = "콜라보 글 카테고리 별 조회 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabPostResponseDTO.CollabPostPreviewListDTO> getCollaboPostList(@RequestParam("categories") List<Long> categories,
                                                                                          @RequestParam(name = "page") Integer page,
                                                                                          @RequestParam(defaultValue = "endDate") String sortBy) {

        Page<CollabPost> collabPostPage = collabPostQueryService.getCollabPostListByCategory(categories, page, sortBy);

        return ApiResponse.onSuccess(CollabPostConverter.collabPostPreviewListDTO(collabPostPage));
    }



}
