package com.partnerd.web.controller.collabRequestController;


import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.converter.collabAskConverter.CollabAskConverter;
import com.partnerd.domain.mapping.CollabAsk;
import com.partnerd.service.collabRequestService.CollabAskCommandService;
import com.partnerd.service.collabRequestService.CollabAskQueryService;
import com.partnerd.web.dto.collabDTO.request.CollabAskRequestDTO;
import com.partnerd.web.dto.collabDTO.response.CollabAskResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collabRequest")
@RequiredArgsConstructor
public class CollabAskRestController {


    private final CollabAskCommandService collabAskCommandService;
    private final CollabAskQueryService collabAskQueryService;

    // 콜라보 요청하기
    @PostMapping("/")
    @Operation(summary = "콜라보 요청 API", description = "콜라보 요청 할 수 있는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<CollabAskResponseDTO.addCollabAskResponseDTO> addCollabAsk(@RequestBody CollabAskRequestDTO.addCollabAskRquestDTO requestDTO) {

        CollabAsk collabAsk = collabAskCommandService.addCollabAsk(requestDTO);

        return ApiResponse.onSuccess(CollabAskConverter.toAddCollabAskResponseDTO(collabAsk));

    }

    // 콜라보 요청 조회하기
    @GetMapping("/")
    @Operation(summary = "콜라보 요청 조회 API", description = "콜라보 요청을 조회 할 수 있는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<CollabAskResponseDTO.CollabAskPreviewListDTO> getCollabAsk(
            @RequestParam (name = "page") Integer page,
            @RequestParam (name = "memberId") Long memberId,
            @RequestParam(name = "askType") Integer askType) {

        Page<CollabAsk> collabAskPage = collabAskQueryService.getCollabAskList(page, askType, memberId);

        return ApiResponse.onSuccess(CollabAskConverter.collabAskPreviewListDTO(collabAskPage));
    }
}
