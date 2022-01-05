package com.ertogrul.omsb2b.service.dtos.manager;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NewManager extends ManagerDTO {

    private String username;

    private String password;

    private boolean superAdmin;

}
