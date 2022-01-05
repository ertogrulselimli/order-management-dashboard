package com.ertogrul.omsb2b.service.dtos.manager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@NoArgsConstructor
@Getter
@Setter
public class ManagerActivityDTO {

    private String sessionId;

    private String userLogin;

    private String ipAddress;

    private String page;

    private Instant time;

}
