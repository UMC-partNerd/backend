package com.partnerd.web.controller.collabPostController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.apiPaylaod.code.status.ErrorStatus;
import com.partnerd.apiPaylaod.exception.handler.CollabPostHandler;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.collabPostConverter.CollabPostConverter;
import com.partnerd.domain.CollabPost;
import com.partnerd.service.collabPostService.CollabPostCommandService;
import com.partnerd.service.collabPostService.CollabPostQueryService;
import com.partnerd.web.dto.collabDTO.request.CollabPostRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabPostResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/collabPosts")
@RequiredArgsConstructor
public class CollabPostRestController {

    private final CollabPostCommandService collabPostCommandService;
    private final CollabPostQueryService collabPostQueryService;
    private final JwtTokenProvider jwtTokenProvider;

    // 콜라보 글 생성
    @PostMapping
    @Operation(summary = "콜라보 글 생성 API",description = "콜라보 글을 생성하는 API입니다.")
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

    // 콜라보 글 수정
    @PatchMapping("/{collabPostId}")
    @Operation(summary = "콜라보 글 수정 API",description = "콜라보 글을 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabPostResponseDTO.addCollabPostResultDTO> modifyCollabPost(@RequestHeader("Authorization") String authorizationHeader,
                                                                                      @PathVariable Long collabPostId,
                                                                                      @RequestBody CollabPostRequestDTO.RequestCollabPostDTO requestDTO) {

        if (requestDTO.getBannerKeyName() == null || requestDTO.getMainKeyName() == null) {
            throw new CollabPostHandler(ErrorStatus.COLLAB_POST_BAD_REQUEST);
        }

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        CollabPost collabPost = collabPostCommandService.modifyCollabPost(collabPostId, requestDTO, memberId);
  
        return ApiResponse.onSuccess(CollabPostConverter.toCollabPostResultDTO(collabPost));
    }

    // 콜라보 글 삭제
    @DeleteMapping("/{collabPostId}")
    @Operation(summary = "콜라보 글 삭제 API",description = "콜라보 글을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<Long> deleteCollabPost(@RequestHeader("Authorization") String authorizationHeader,
                                              @PathVariable(name = "collabPostId") Long collabPostId) {

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        collabPostCommandService.deleteCollabPost(collabPostId, memberId);
        return ApiResponse.onSuccess(collabPostId);
    }

    // 콜라보 글 전체 조회 (최신순)
    @GetMapping
    @Operation(summary = "콜라보 글 전체 조회 API (마감순, 최신순)",
            description = "콜라보 글 전체 조회 API입니다. pageNum은 1부터 시작합니다." +
                    "sortBy는 기본값이 endDate(마감순)입니다. createdAt을 입력하면 최신순 정렬이 가능합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<CollabPostResponseDTO.PagingResultDTO> getCollaboPostList(
            @RequestParam(defaultValue = "endDate") String sortBy,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedAt,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date lastEndDate,
            @RequestParam(required = false) Long lastId
    ) {
        // DTO로 변환
        CollabPostRequestDTO.RequestNoOffsetPagingDTO requestNoOffsetPagingDTO =
                new CollabPostRequestDTO.RequestNoOffsetPagingDTO(sortBy, size, pageNum, lastCreatedAt, lastEndDate, lastId);

        CollabPostResponseDTO.PagingResultDTO<CollabPostResponseDTO.CollabPostPreviewDTO> collabPostPage =
                collabPostQueryService.getCollabPostList(requestNoOffsetPagingDTO);

        return ApiResponse.onSuccess(collabPostPage);
    }


    // 콜라보 글 상세 조회
    @GetMapping("/{collabPostId}")
    @Operation(summary = "콜라보 글 상세 조회 API 구현 ",description = "콜라보 글 상세 조회 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabPostResponseDTO.CollabPostDetailDTO> getCollaboPostList(@PathVariable(name = "collabPostId") Long collabPostId) {

              CollabPost collabPost = collabPostQueryService.getCollabPost(collabPostId);

        return ApiResponse.onSuccess(CollabPostConverter.toCollabPostDetailDTO(collabPost));

    }

    // 카테고리 별 콜라보 글 조회
/*    @GetMapping("/categories")
    @Operation(summary = "콜라보 글 카테고리 별 조회 API (마감순, 최신순) ",
            description = "콜라보 글 카테고리 별 조회 API입니다. page는 1부터 시작합니다." +
                    "sortBy 는 정렬기준으로 기본값은 endDate(마감순) 입니다. createdAt 을 입력하면 콜라보 글 등록 최신순으로 정렬할 수 있습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabPostResponseDTO.CollabPostPreviewListDTO> getCollaboPostList(@RequestParam("categories") List<Long> categories,
                                                                                          @RequestParam(name = "page") Integer page,
                                                                                          @RequestParam(defaultValue = "endDate") String sortBy) {

        Page<CollabPost> collabPostPage = collabPostQueryService.getCollabPostListByCategory(categories, page-1, sortBy);

        return ApiResponse.onSuccess(CollabPostConverter.collabPostPreviewListDTO(collabPostPage));
    }*/

    // 마이페이지 - 내가 쓴 콜라보레이션 모아보기
    @GetMapping("/mypage")
    @Operation(summary = "마이페이지 내가 쓴 콜라보레이션 목록 조회 API",description = "마이페이지의 내가 쓴 글 페이지에서 콜라보레이션 목록을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<CollabPostResponseDTO.MypageCollabPostPreviewListDTO> getMyCollabPosts(@RequestHeader("Authorization") String authorizationHeader){
        // 1. JWT 토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");

        // 2. 토큰에서 userId 추출
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        // 3. 서비스 호출
        List<CollabPost> collabPosts = collabPostQueryService.getMyCollabPosts(memberId);

        return ApiResponse.onSuccess(CollabPostConverter.toMyCollabPostsDTO(memberId, collabPosts));
    }

    @DeleteMapping("/all")
    @Operation(summary = "콜라보 글 전체삭제 ",
            description = "콜라보 글 일정 글 이상 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<String> deleteCollabPostAll(@RequestHeader("Authorization") String authorizationHeader,
                                              @RequestParam(name = "collabPostId") Long collabPostId) {

       collabPostCommandService.deleteCollabPostAllById(collabPostId);
        return ApiResponse.onSuccess("성공");
    }
}
