package com.partnerd.web.controller.collabAskController;


import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.converter.collabAskConverter.CollabAskConverter;
import com.partnerd.domain.mapping.CollabAsk;
import com.partnerd.service.collabAskService.CollabAskCommandService;
import com.partnerd.service.collabAskService.CollabAskQueryService;
import com.partnerd.web.dto.collabDTO.request.CollabAskRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabAskResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collabAsks")
@RequiredArgsConstructor
public class CollabAskRestController {


    private final CollabAskCommandService collabAskCommandService;
    private final CollabAskQueryService collabAskQueryService;
    private final JwtTokenProvider jwtTokenProvider;

    // 콜라보 요청하기
    @PostMapping("/{collabPostId}")
    @Operation(summary = "콜라보 요청 API", description = "콜라보 요청 할 수 있는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<CollabAskResponseDTO.addCollabAskResponseDTO> addCollabAsk(@RequestHeader("Authorization") String authorizationHeader,
                                                                                  @PathVariable(name = "collabPostId")Long collabPostId) {

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        CollabAsk collabAsk = collabAskCommandService.addCollabAsk(collabPostId, memberId);

        return ApiResponse.onSuccess(CollabAskConverter.toAddCollabAskResponseDTO(collabAsk));

    }

    // 콜라보 요청 취소하기
    @DeleteMapping("/{collabAskId}")
    @Operation(summary = "콜라보 요청 취소 API", description = "콜라보 요청을 취소 할 수 있는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<Long> deleteCollabAsk(@RequestHeader("Authorization") String authorizationHeader,
                                             @PathVariable(name = "collabAskId") Long collabAskId) {

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        collabAskCommandService.deleteCollabAsk(collabAskId, memberId);

        return ApiResponse.onSuccess(collabAskId);

    }


    // 콜라보 요청 조회하기
    @GetMapping("/")
    @Operation(summary = "콜라보 요청 조회 API", description = "콜라보 요청을 조회 할 수 있는 API입니다. " +
            "page는 1부터 시작합니다. askType 0은 보낸요청, 1은 받은 요청입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<CollabAskResponseDTO.CollabAskPreviewListDTO> getCollabAsk(
            @RequestParam (name = "page") Integer page,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(name = "askType") Integer askType) {

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        Page<CollabAsk> collabAskPage = collabAskQueryService.getCollabAskList(page - 1, askType, memberId);

        return ApiResponse.onSuccess(CollabAskConverter.collabAskPreviewListDTO(collabAskPage));
    }
}
