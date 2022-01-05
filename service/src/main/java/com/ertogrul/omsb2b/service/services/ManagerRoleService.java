package com.ertogrul.omsb2b.service.services;


import com.ertogrul.omsb2b.common.exception.StandardException;
import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.persistence.entities.ManagerMenu;
import com.ertogrul.omsb2b.persistence.entities.ManagerRole;
import com.ertogrul.omsb2b.persistence.repositories.ManagerMenuRepository;
import com.ertogrul.omsb2b.persistence.repositories.ManagerRolesRepository;
import com.ertogrul.omsb2b.service.dtos.roles.ListRole;
import com.ertogrul.omsb2b.service.dtos.roles.NewRole;
import com.ertogrul.omsb2b.service.dtos.roles.ViewRole;
import com.ertogrul.omsb2b.service.dtos.roles.EditRole;
import com.ertogrul.omsb2b.service.exceptions.RoleManagersExistsException;
import com.ertogrul.omsb2b.service.mappers.MenuMapper;
import com.ertogrul.omsb2b.service.mappers.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
public class ManagerRoleService {


    @Autowired
    RoleMapper roleMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    ManagerRolesRepository rolesRepository;

    @Autowired
    ManagerMenuRepository managerMenuRepository;


    @Transactional(readOnly = true)
    public Page<ListRole> datatable(DatatableRequest request){
        final Page<ManagerRole> datatable = rolesRepository.datatable(request);
        return   datatable.map(t->roleMapper.toListRoleDTO(t));
    }


    public ViewRole createNewRole(NewRole roleDTO){
       final ManagerRole managerRoles = roleMapper.toManagerRole(roleDTO);
       roleDTO.getMenus().stream().map(t -> {
               final ManagerMenu managerMenu = menuMapper.toManagerMenu(t);
               return managerMenu;
       }).forEach(c -> managerRoles.addMenu(c));
       return   roleMapper.toViewRoleDTO(rolesRepository.save(managerRoles));
    }


    public ViewRole updateRole(EditRole dto){
        final ManagerRole roles = rolesRepository.findById(dto.getId()).orElseThrow(() -> new StandardException("Role not found with id " + dto.getId()));
        roleMapper.editRole(dto,roles);
        roles.getMenus().clear();;
        dto.getMenus().stream().map(t -> {
                final ManagerMenu managerMenu = menuMapper.toManagerMenu(t);
                return managerMenu;
        }).forEach(c -> roles.addMenu(c));
      return roleMapper.toViewRoleDTO(rolesRepository.save(roles));
    }



    @Transactional(readOnly = true)
    public List<ListRole> selectRoles(){
        final List<ManagerRole> all = rolesRepository.findAll();
      return   roleMapper.toListRoleDTOS(all);
    }


    @Transactional(readOnly = true)
    public ViewRole getRoleById(final Long id){
        final ManagerRole roles = rolesRepository.findById(id).orElseThrow(() -> new StandardException("Role not found with id " + id));
        return roleMapper.toViewRoleDTO(roles);
    }



    public void deleteRole(final Long id){
        final ManagerRole roles = rolesRepository.findById(id).orElseThrow(() -> new StandardException("Role not found with id " + id));
        if(!roles.getManagers().isEmpty()){
            final List<String> collect = roles.getManagers().stream().map(t -> t.getUsername()).collect(Collectors.toList());
            throw new RoleManagersExistsException("Bu rola bağlı istifadəçilər var ",collect);
        }
        rolesRepository.delete(roles);
    }

}
