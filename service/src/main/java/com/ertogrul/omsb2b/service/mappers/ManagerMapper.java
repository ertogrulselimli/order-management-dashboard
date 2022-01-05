package com.ertogrul.omsb2b.service.mappers;


import com.ertogrul.omsb2b.persistence.entities.Manager;
import com.ertogrul.omsb2b.service.dtos.manager.EditManager;
import com.ertogrul.omsb2b.service.dtos.manager.ListManager;
import com.ertogrul.omsb2b.service.dtos.manager.NewManager;
import com.ertogrul.omsb2b.service.dtos.manager.ViewManager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses = {RoleMapper.class})
public interface ManagerMapper {

     ListManager toListManagerDTO(Manager manager);

     List<ListManager> toListManagerDTOS(List<Manager> managers);


     Manager toManager(NewManager dto);


     @Mappings({
             @Mapping(source = "manufacturePoint.id",target = "manufacturePointId"),
             @Mapping(source = "manufacturePoint.name",target = "manufacturePointName"),
             @Mapping(source = "distributor.name",target = "distributorName"),
             @Mapping(source = "distributor.id",target = "distributorId"),
             @Mapping(source = "role.id",target = "roleId"),
             @Mapping(source = "role.name",target = "roleName")
     })
     ViewManager toViewManagerDTO(Manager manager);



     @Mappings({
             @Mapping(target = "password",ignore = true),
             @Mapping(target = "role",ignore = true),
             @Mapping(target = "manufacturePoint",ignore = true),
             @Mapping(target = "distributor",ignore = true)
     })
     void editManager(EditManager dto, @MappingTarget Manager manager);

}
