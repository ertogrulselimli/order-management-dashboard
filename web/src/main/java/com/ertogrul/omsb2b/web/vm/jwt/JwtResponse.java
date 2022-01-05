package com.ertogrul.omsb2b.web.vm.jwt;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class JwtResponse {

    private String jwttoken;

    @JsonProperty("redirectToChangePassword")
    private boolean redirectToChgPassword=false;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }
}
