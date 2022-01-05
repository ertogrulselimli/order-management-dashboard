package com.ertogrul.omsb2b.persistence.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "DASHBOARD_MANAGER_ROLES")
@Entity
public class ManagerRole extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long id;


    @Column(name = "ROLE_NAME",unique = true,nullable = false)
    private String name;

    @Column(name = "ROLE_DESCRIPTION")
    private String description;


    @OneToMany(mappedBy = "role")
    private Set<Manager> managers=new HashSet<>();



    @OneToMany(
            mappedBy = "managerRole",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ManagerMenu> menus=new HashSet<>();



    public void addMenu(ManagerMenu menu) {
        menus.add(menu);
        menu.setManagerRole(this);
    }

    public void removeMenu(ManagerMenu menu) {
        menus.remove(menu);
        menu.setManagerRole(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerRole roles = (ManagerRole) o;
        return id.equals(roles.id) &&
                Objects.equals(name, roles.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
