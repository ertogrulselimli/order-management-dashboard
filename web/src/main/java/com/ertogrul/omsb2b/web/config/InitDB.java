package com.ertogrul.omsb2b.web.config;

import com.ertogrul.omsb2b.common.constants.RoleConstants;
import com.ertogrul.omsb2b.service.dtos.manager.NewManager;
import com.ertogrul.omsb2b.service.dtos.manager.ViewManager;
import com.ertogrul.omsb2b.service.dtos.roles.NewRole;
import com.ertogrul.omsb2b.service.dtos.roles.ViewRole;
import com.ertogrul.omsb2b.service.mappers.MenuMapper;
import com.ertogrul.omsb2b.service.mappers.RoleMapper;
import com.ertogrul.omsb2b.service.services.ManagerRoleService;
import com.ertogrul.omsb2b.service.services.ManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.io.IOException;

@Profile("initDB")
@Component
public class InitDB {

    @Autowired
    ManagerRoleService roleService;

    @Autowired
    ManagerService managerService;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    ObjectMapper objectMapper;


    @PostConstruct
    public void init() throws IOException {
      initDashboardCredentials();
      //initMockData();
    }


    @Transactional
    public void initDashboardCredentials(){

      /*  NewRole roleDTO=new NewRole(); // as here we have specified superAdmin field in manager so we don't need Role SuperAdmin anymore
        roleDTO.setDescription("Superadmin");
        roleDTO.setMenuRole(false); //Note that SuperAdmin we don't need to specify menus it has default access to all menus and roles
        roleDTO.setName(RoleConstants.SUPERADMIN);
        final ViewRole superAdminRole = roleService.createNewRole(roleDTO);*/


        NewRole distRole=new NewRole();
        distRole.setDescription("Distributor");
        distRole.setName(RoleConstants.DISTRIBUTOR);//we dont need to specify default is menuRole is true
     /*   NewMenuDTO createArticle=new NewMenuDTO("CreateArticle");
        testRole.getMenus().add(createArticle);*/
        final ViewRole testRole1 = roleService.createNewRole(distRole);


        NewRole mnfRole=new NewRole();
        mnfRole.setDescription("İstehsalçı");
        mnfRole.setName(RoleConstants.MANUFACTURER);
        roleService.createNewRole(mnfRole);


        NewRole supervisorRole=new NewRole();
        supervisorRole.setDescription("Supervisor");
        supervisorRole.setName(RoleConstants.SUPERVISOR);
        roleService.createNewRole(supervisorRole);


        NewManager superAdmin=new NewManager();
        superAdmin.setEmail("ertogrul.selimli@gmail.com");
        superAdmin.setUsername("admin");
        superAdmin.setPassword("Aa@@123123");
        superAdmin.setFirstname("Ertogrul");
        superAdmin.setLastname("Selimli");
        superAdmin.setSuperAdmin(true);


        final ViewManager newManager = managerService.newManager(superAdmin);
        //As this application is used  between  Manufacturer Distributer and Central Autorized Person There will be 3 main roles
        //ROLE_DISTRIBUTOR
        //ROLE_MANUFACTURER
        //ROLE_SUPERVISOR

    }


}
