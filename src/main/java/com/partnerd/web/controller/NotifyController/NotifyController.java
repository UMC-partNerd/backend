package com.partnerd.web.controller.NotifyController;

import com.partnerd.config.security.JwtTokenProvider;
import com.partnerd.service.notifyService.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotifyController {

    private final NotificationServiceImpl notifyService;
    private final JwtTokenProvider jwtTokenProvider;
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestHeader("Authorization") String authorizationHeader,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {

        String token = authorizationHeader.replace("Bearer ", "");
        Long memberId = Long.valueOf(jwtTokenProvider.getClaims(token).getSubject());

        return notifyService.subscribe(memberId, lastEventId);
    }
}
