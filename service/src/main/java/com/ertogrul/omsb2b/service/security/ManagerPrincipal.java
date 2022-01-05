package com.ertogrul.omsb2b.service.security;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Setter
@Getter
public class ManagerPrincipal extends User {

    private String firstName;

    private String lastName;

    private boolean isSuperAdmin;


    public ManagerPrincipal(String firstName,
                            String lastName,
                            boolean isSuperAdmin,
                            String username,
                            String password,
                            boolean enabled,
                            boolean accountNonExpired,
                            boolean credentialsNonExpired,
                            boolean accountNonLocked,
                            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.firstName=firstName;
        this.lastName=lastName;
        this.isSuperAdmin=isSuperAdmin;
    }



    public ManagerPrincipal(String username,
                            String password,
                            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
