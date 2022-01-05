package com.ertogrul.omsb2b.service.dtos.roles;

import com.ertogrul.omsb2b.service.dtos.menus.NewMenuDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ertogrul Selimli on 10/24/2021
 * @project IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {

    private Long id;

    private String name;

    private String description;

    private Set<NewMenuDTO> menus=new HashSet<>();

}
