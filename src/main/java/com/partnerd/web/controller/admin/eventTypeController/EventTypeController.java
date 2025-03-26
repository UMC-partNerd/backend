package com.partnerd.web.controller.admin.eventTypeController;

import com.partnerd.apiPaylaod.ApiResponse;
import com.partnerd.converter.collabPostConverter.EventTypeConverter;
import com.partnerd.mongoRepository.domain.EventType;
import com.partnerd.service.eventTypeService.EventTypeService;
import com.partnerd.web.dto.eventTypeDTO.EventTypeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/eventTypes")
@RequiredArgsConstructor
public class EventTypeController {

    private final EventTypeService eventTypeService;

    @Operation(summary = "이벤트 유형 생성 API",description = "이벤트 유형을 생성할 수 있는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @PostMapping("/")
    public ApiResponse<EventTypeDTO> addEventType(@RequestParam(name = "name") String name) {
        EventType eventType = eventTypeService.addEventType(name);

        return ApiResponse.onSuccess(EventTypeConverter.toEventTypeDTO(eventType));
    }
}
