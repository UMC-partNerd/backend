package com.partnerd.service.eventTypeService.impl;

import com.partnerd.domain.EventType;
import com.partnerd.repository.collabPostRepository.EventTypeRepository;
import com.partnerd.service.eventTypeService.EventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventTypeServiceImpl implements EventTypeService {

    private final EventTypeRepository eventTypeRepository;
    @Override
    public EventType addEventType(String name) {
        EventType eventType = EventType.builder()
                .name(name)
                .build();
        return eventTypeRepository.save(eventType);
    }
}
