package com.ertogrul.omsb2b.service.dtos.roles;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"menus"})
public class ListRole extends RoleDTO{

}
