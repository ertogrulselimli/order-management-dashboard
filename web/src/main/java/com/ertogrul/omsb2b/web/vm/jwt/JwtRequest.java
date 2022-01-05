package com.ertogrul.omsb2b.web.vm.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JwtRequest {

    private String username;
    private String password;

    private boolean rememberMe;
}
