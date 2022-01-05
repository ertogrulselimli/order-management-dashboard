package com.ertogrul.omsb2b.service.dtos.manager;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ListManager extends ManagerDTO {

    private String username;

    @JsonProperty("active")
    private boolean active;

}
