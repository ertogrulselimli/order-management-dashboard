package com.ertogrul.omsb2b.web.security.jwt;


import com.ertogrul.omsb2b.common.constants.RoleConstants;
import com.ertogrul.omsb2b.persistence.entities.Manager;
import com.ertogrul.omsb2b.persistence.entities.ManagerRole;
import com.ertogrul.omsb2b.persistence.repositories.ManagerRepository;
import com.ertogrul.omsb2b.service.security.ManagerPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;



@Component("managerDetailService")
public class ManagerDetailService implements UserDetailsService {


    private final ManagerRepository managerRepository;


    public ManagerDetailService(ManagerRepository managerRepository){
        this.managerRepository=managerRepository;
    }



    @Transactional(readOnly = true)
    @Override
    public ManagerPrincipal loadUserByUsername(String username)  {
        final Manager manager = managerRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
        ManagerPrincipal principial = new ManagerPrincipal(manager.getFirstname(),manager.getLastname(),manager.isSuperAdmin(),username, manager.getPassword(),
                manager.isActive(), true, true, true,
                getAuthorities(manager));
        return principial;
    }



    private Collection<? extends GrantedAuthority> getAuthorities(Manager manager) {
        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        if(manager.isSuperAdmin()){
            authorities.add(new SimpleGrantedAuthority(RoleConstants.SUPERADMIN));
        }else {
            final ManagerRole role = manager.getRole();
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            final Set<SimpleGrantedAuthority> collect = role.getMenus().stream().map(t -> new SimpleGrantedAuthority(t.getName())).collect(Collectors.toSet());
            authorities.addAll(collect);
        }
        return authorities;
    }


}
