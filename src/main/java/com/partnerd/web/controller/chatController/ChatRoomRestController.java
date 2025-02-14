package com.partnerd.web.controller.chatController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.domain.ChatRoom;
import com.partnerd.service.chatRoomService.ChatRoomCommandService;
import com.partnerd.service.chatRoomService.ChatRoomQueryService;
import com.partnerd.web.dto.chatRoomDTO.response.ChatRoomResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatRooms")
@RequiredArgsConstructor
public class ChatRoomRestController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomCommandService chatRoomCommandService;
    private final ChatRoomQueryService chatRoomQueryService;

    // 콜라보 채팅방 불러오기
    @GetMapping("/collab")
    @Operation(summary = "사용자가 참여하는 콜라보 채팅방 조회 API", description = "콜라보 요청을 할 수 있는 동아리 리더진들만 조회할 수 있는 콜라보 채팅방 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<ChatRoomResponseDTO.CollabChatRoomPreviewListDTO> getCollabChatRoomList(@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestParam(required = false) Long cursor, // 이전 페이지의 마지막 ID (없으면 첫 페이지)
                                             @RequestParam(defaultValue = "10") int size) { // 한 페이지에 가져올 데이터 개수


        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        return ApiResponse.onSuccess(chatRoomQueryService.getCollabChatRoomList(memberId, cursor, size));

    }

    // 개인 채팅방 불러오기
    @GetMapping("/private")
    @Operation(summary = "사용자가 참여하는 개인 채팅방 조회 API", description = "사용자가 참여하는 개인 채팅방 조회 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<?> getPrivateChatRoomList (@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestParam(required = false) Long cursor, // 이전 페이지의 마지막 ID (없으면 첫 페이지)
                                             @RequestParam(defaultValue = "10") int size) { // 한 페이지에 가져올 데이터 개수


        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        return ApiResponse.onSuccess(chatRoomQueryService.getPrivateChatRoomList(memberId, cursor, size));
    }

    // 콜라보 요청 시 chatRoom 생성
    @PostMapping("/collab/{collabAskId}")
    @Operation(summary = "콜라보 요청 채팅방 생성 API", description = "콜라보 요청 채팅방을 생성할 수 있는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<Long> createCollabChatRoom (@RequestHeader("Authorization") String authorizationHeader,
                                                                                  @PathVariable(name = "collabAskId")Long collabAskId) {

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        ChatRoom chatRoom = chatRoomCommandService.createCollabChatRoom(collabAskId, memberId);

        return ApiResponse.onSuccess(chatRoom.getId());
    }

    // 컨택트 시 chatRoom 생성
    @PostMapping("/private/{nickname}")
    @Operation(summary = "컨택트 채팅방 생성 API", description = "컨택트 채팅방을 생성할 수 있는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<Long> createContactChatRoom (@RequestHeader("Authorization") String authorizationHeader,
                                                   @PathVariable(name = "nickname") String contactMemberNickname) {

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        ChatRoom chatRoom = chatRoomCommandService.createContactChatRoom(memberId, contactMemberNickname);

        return ApiResponse.onSuccess(chatRoom.getId());
    }


}
