package com.partnerd.service.notifyService.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CollabAskEvent {

    private Long receiverId;
    private String clubName;
    private String CollabPostTitle;

}
