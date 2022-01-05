package com.ertogrul.omsb2b.service.dtos.roles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"bookName", "bookCategory"})
public class NewRole extends RoleDTO{

}
