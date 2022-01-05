package com.ertogrul.omsb2b.web.controllers;

/* Important notes
 * In order Dashboard backend work properly along with frontEnd viewJs  we must follow some conventions
 * whenever you develop new Crud operations
 * You have to have 5 endpoints ListEntity,ViewEntity,EditEntity,CreateEntity ,DeleteEntity
 * PreAuthorize haseAnyAuthority roles names must follow this convention rules
 * hasAuthority('Edit{entityName}')
 * or hasAnyAuthority('Edit{EntityName}','View{EntityName}'
 *
 *example delete enpoint
 *
 * @PreAuthorize("endpointAccess('DeleteEntity')")
 * @DeleteMapping(value="/delete")
 * public void deleteEntity(){
 *
 * }
 *  */

import com.ertogrul.omsb2b.persistence.datatable.DatatableRequest;
import com.ertogrul.omsb2b.service.dtos.manager.EditManager;
import com.ertogrul.omsb2b.service.dtos.manager.ListManager;
import com.ertogrul.omsb2b.service.dtos.manager.NewManager;
import com.ertogrul.omsb2b.service.dtos.manager.ViewManager;
import com.ertogrul.omsb2b.service.dtos.roles.EditRole;
import com.ertogrul.omsb2b.service.dtos.roles.ListRole;
import com.ertogrul.omsb2b.service.dtos.roles.NewRole;
import com.ertogrul.omsb2b.service.dtos.roles.ViewRole;
import com.ertogrul.omsb2b.service.services.ManagerRoleService;
import com.ertogrul.omsb2b.service.services.ManagerService;
import com.ertogrul.omsb2b.service.services.TokenBlockListService;
import com.ertogrul.omsb2b.service.services.filters.ManagerFilter;
import com.ertogrul.omsb2b.web.security.jwt.JwtTokenProvider;
import com.ertogrul.omsb2b.web.vm.jwt.ChangePassword;
import com.ertogrul.omsb2b.web.vm.jwt.JwtRequest;
import com.ertogrul.omsb2b.web.vm.jwt.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/api")
public class ManagerController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ManagerRoleService managerRoleService;

    @Autowired
    TokenBlockListService tokenBlockListService;


    /* Do not touch here  logic is here if it is the first time user is authenticating he must change password */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword());
        Authentication auth = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        JwtResponse jwtResponse=new JwtResponse();
        String jwt="";
        //change password
        if(managerService.checkFirstTimeAuthenticated()){
            auth=new UsernamePasswordAuthenticationToken(auth.getPrincipal(),auth.getCredentials(),new ArrayList<>());//empty authorities list becuase not fully authenticated
            jwtResponse.setRedirectToChgPassword(true);
            jwt = tokenProvider.createToken(auth,false);
            //end change password
        }else {
            jwt = tokenProvider.createToken(auth,false);
            managerService.addLastJwtToken(authenticationRequest.getUsername(), jwt);
        }

        jwtResponse.setJwttoken(jwt);
        return ResponseEntity.ok(jwtResponse);
    }



    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/change-password")
    public void changePassword(@RequestBody ChangePassword changePassword, HttpServletRequest httpServletRequest, Principal principal){
        managerService.changePassword(changePassword.getNewPassword(),changePassword.getConfirmPassword());
        managerService.addLastJwtToken(principal.getName(),httpServletRequest.getHeader("Authorization").substring(7));
    }



    @PostMapping(value = "/logout")
    @ResponseBody
    public void logout(HttpServletRequest httpServletRequest,Authentication authentication){
        final String authorization = httpServletRequest.getHeader("Authorization");
        tokenBlockListService.store(authorization.substring(7));
    }


  /*  *//* Do not touch here *//*we  no longer needed for this
    @GetMapping(value = "/manager-info")
    public ResponseEntity<ManagerInfoDTO> getManagerInfo(HttpServletRequest httpServletRequest){
        final String token = httpServletRequest.getHeader("Authorization");
        final Authentication authentication = tokenProvider.getAuthentication(token.substring(7));
        final ManagerInfoDTO managerByUsername = managerService.getManagerByUsername(authentication.getName());
        return ResponseEntity.ok(managerByUsername);
    }
*/

    /* Start roles part */
    @PreAuthorize("endpointAccess('ListRole')")
    @PostMapping(value = "/manager-roles")
    public ResponseEntity<Page<ListRole>> listRole(@RequestBody DatatableRequest request){
        return ResponseEntity.ok(managerRoleService.datatable(request));
    }


    @GetMapping(value = "/roles-select")
    public ResponseEntity<List<ListRole>> listRoles(){
        return ResponseEntity.ok(managerRoleService.selectRoles());
    }


    @PreAuthorize("endpointAccess('ViewRole')")
    @GetMapping(value = "/manager-roles/{id}")
    public ResponseEntity<ViewRole> viewRole(@PathVariable("id") final Long id){
        return ResponseEntity.ok(managerRoleService.getRoleById(id));
    }



    @PreAuthorize("endpointAccess('CreateRole')")
    @PostMapping(value = "/manager-role")
    public void createRole(@RequestBody NewRole request){
        managerRoleService.createNewRole(request);
    }


    @PreAuthorize("endpointAccess('EditRole')")
    @PostMapping(value = "/manager-role/update")
    public void editRole(@RequestBody EditRole request){
        managerRoleService.updateRole(request);
    }


    @PreAuthorize("endpointAccess('DeleteRole')")
    @PostMapping(value = "/manager-role/delete/{id}")
    public void deleteRole(@PathVariable("id") final Long id){
        managerRoleService.deleteRole(id);
    }

    /*  End roles part  */


    /*  Start  managers part  */
    @PreAuthorize("endpointAccess('ListManager')")
    @PostMapping(value = "/manager-list")
    public ResponseEntity<Page<ListManager>>  listManager(@RequestBody DatatableRequest<ManagerFilter> request){
        return ResponseEntity.ok(managerService.datatable(request));
    }


    @PreAuthorize("endpointAccess('ViewManager')")
    @GetMapping(value = "/manager/{id}")
    public ResponseEntity<ViewManager> viewManager(@PathVariable("id") final Long id){
        return ResponseEntity.ok(managerService.getManagerById(id));
    }


    @PreAuthorize("endpointAccess('NewManager')")
    @PostMapping(value = "/manager")
    public ResponseEntity<ViewManager> newManager(@RequestBody NewManager newManager){
        return ResponseEntity.ok(managerService.newManager(newManager));
    }


    @PreAuthorize("endpointAccess('EditManager')")
    @PostMapping(value = "/manager/update")
    @ResponseBody
    public void editManager(@RequestBody EditManager request){
        managerService.editManager(request);
    }



    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    @PostMapping(value = "/manager/{id}/block")
    @ResponseBody
    public void blockManager(@PathVariable("id") final Long managerId){
        managerService.blockManager(managerId);
    }



    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    @PostMapping(value = "/manager/{id}/unblock")
    @ResponseBody
    public void unBlockManager(@PathVariable("id") final Long managerId){
        managerService.unBlockManager(managerId);
    }



    @PreAuthorize("endpointAccess('DeleteManager')")
    @PostMapping(value = "/manager/delete/{id}")
    @ResponseBody
    public void deleteManager(@PathVariable("id") final Long managerId){
        managerService.deleteManager(managerId);
    }
    /*  end manager part  */



    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
