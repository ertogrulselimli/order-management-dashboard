package com.ertogrul.omsb2b.service.dtos.manager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ertogrul Selimli on 10/17/2021
 * @project IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
public class ManagerDTO {

    private Long id;

    private String email;

    private String firstname;

    private String lastname;

    private String avatar;

    private Long roleId;

    private String roleName;

    private Long manufacturePointId;

    private String manufacturePointName;

    private Long distributorId;

    private String distributorName;

}
