package com.ertogrul.omsb2b.service.mappers;

import com.ertogrul.omsb2b.persistence.entities.ManagerMenu;
import com.ertogrul.omsb2b.service.dtos.menus.NewMenuDTO;
import com.ertogrul.omsb2b.service.dtos.menus.ListMenuDTO;
import com.ertogrul.omsb2b.service.dtos.menus.ViewMenuDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {

      ListMenuDTO toListMenuDTO(ManagerMenu managerMenu);

      List<ListMenuDTO> toListMenuDTOS(List<ManagerMenu> managerMenus);

      ManagerMenu toManagerMenu(NewMenuDTO dto);

      ViewMenuDTO toViewMenuDTO(ManagerMenu managerMenu);

      ListMenuDTO toListMenuDTO(ViewMenuDTO dto);




}
