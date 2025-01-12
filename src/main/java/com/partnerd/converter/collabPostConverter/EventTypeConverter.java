package com.partnerd.converter.collabPostConverter;

import com.partnerd.domain.EventType;
import com.partnerd.web.dto.eventTypeDTO.EventTypeDTO;

public class EventTypeConverter {
    public static EventTypeDTO toEventTypeDTO (EventType eventType) {
        return EventTypeDTO.builder()
                .id(eventType.getId())
                .name(eventType.getName())
                .build();
    }
}
