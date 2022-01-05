package com.ertogrul.omsb2b.service.services;


import com.ertogrul.omsb2b.service.dtos.manager.ManagerActivityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.Instant;

@Slf4j
@Controller
public class ManagerActivityService {

    @MessageMapping("/activity")
    @SendTo("/topic/tracker")
    public ManagerActivityDTO sendActivity(@Payload ManagerActivityDTO activityDTO,
                                    StompHeaderAccessor stompHeaderAccessor,
                                    Principal principal) {

        activityDTO.setUserLogin(principal.getName());
        activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
        activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get("IP_ADDRESS").toString());
        activityDTO.setTime(Instant.now());
        log.debug("Sending user tracking data {}", activityDTO);
        return activityDTO;
    }
}
