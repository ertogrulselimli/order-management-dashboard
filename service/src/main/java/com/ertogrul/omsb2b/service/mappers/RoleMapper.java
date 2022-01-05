package com.ertogrul.omsb2b.service.mappers;


import com.ertogrul.omsb2b.persistence.entities.ManagerRole;
import com.ertogrul.omsb2b.service.dtos.roles.EditRole;
import com.ertogrul.omsb2b.service.dtos.roles.ListRole;
import com.ertogrul.omsb2b.service.dtos.roles.NewRole;
import com.ertogrul.omsb2b.service.dtos.roles.ViewRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses = {MenuMapper.class})
public interface RoleMapper {

    @Mappings({
            @Mapping(target = "menus",ignore = true)
    })
    ListRole toListRoleDTO(ManagerRole role);

    List<ListRole> toListRoleDTOS(List<ManagerRole> roles);


    @Mappings({
            @Mapping(target = "menus",ignore = true)
    })
    ManagerRole toManagerRole(NewRole dto);


    @Mappings({
            @Mapping(target = "menus",ignore = true)
    })
    void editRole(EditRole dto, @MappingTarget ManagerRole roles);

    ViewRole toViewRoleDTO(ManagerRole roles);

    ListRole toListRoleDTO(ViewRole dto);
}
