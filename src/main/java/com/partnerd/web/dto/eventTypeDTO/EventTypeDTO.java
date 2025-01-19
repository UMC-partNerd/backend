package com.partnerd.web.dto.eventTypeDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EventTypeDTO {
    private Long id;
    private String name;
}
